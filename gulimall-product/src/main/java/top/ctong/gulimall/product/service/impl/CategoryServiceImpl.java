package top.ctong.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;
import top.ctong.gulimall.product.dao.CategoryDao;
import top.ctong.gulimall.product.entity.CategoryEntity;
import top.ctong.gulimall.product.service.CategoryBrandRelationService;
import top.ctong.gulimall.product.service.CategoryService;
import top.ctong.gulimall.product.vo.Catalog2Vo;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * █████▒█      ██  ▄████▄   ██ ▄█▀     ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒      ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░      ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄      ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄     ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒     ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 * Copyright 2021 Clover You.
 * <p>
 * 商品三级分类
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redisson;

    /**
     * 查询叶
     *
     * @param params 自定义查询条件
     * @return PageUtils
     * @author Clover You
     * @date 2021/11/21 20:36
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(new Query<CategoryEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    /**
     * 查询所有分类并构造树形结构
     *
     * @return List<CategoryEntity>
     * @author Clover You
     * @date 2021/11/21 20:36
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        // 查出所有分类
        // 没有查询条件selectList可为null
        List<CategoryEntity> entities = baseMapper.selectList(null);
        // 组装成树形结构
        // 找到所有一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter((category) -> category.getParentCid() == 0).map(
            menu -> {
                // 查找子分类
                menu.setChildren(getChildren(menu, entities));
                return menu;
            }).sorted((m1, m2) -> {
            // 排序，小的在左边，大的在右边
            return (m1.getSort() == null ? 0 : m1.getSort()) - (m2.getSort() == null ? 0 : m2.getSort());
        }).collect(Collectors.toList());

        return level1Menus;
    }

    /**
     * 通过菜单id删除菜单
     *
     * @param asList id列表
     * @author Clover You
     * @date 2021/11/22 14:48
     */
    @Override
    public void removeMenuByIds(List<Long> asList) {
        // 检查当前菜单是否有子菜单
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * 找到指定菜单的子菜单
     *
     * @param root 当前指定菜单
     * @param all  全部菜单
     * @return List<CategoryEntity>
     * @author Clover You
     * @date 2021/11/21 20:54
     */
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().filter(entity -> {
            return entity.getParentCid().equals(root.getCatId());
        }).map(menu -> {
            // 为当前菜单查找子菜单
            menu.setChildren(getChildren(menu, all));
            return menu;
        }).sorted((m1, m2) -> {
            return (m1.getSort() == null ? 0 : m1.getSort()) - (m2.getSort() == null ? 0 : m2.getSort());
        }).collect(Collectors.toList());
        return children;
    }

    /**
     * 通过分组id查找分组路径[父/子/孙]
     *
     * @param catelogId 分组id
     * @return Long
     * @author Clover You
     * @date 2021/11/27 08:52
     */
    @Override
    public Long[] findCategoryPath(Long catelogId) {
        List<Long> path = findParentPath(catelogId, new ArrayList<>(3));
        Collections.reverse(path);
        return path.toArray(new Long[0]);
    }

    /**
     * 通过子孙节点找所有父节点
     *
     * @param catelogId 要查找的子孙节点id
     * @param path      节点集合
     * @return List<Long>
     * @author Clover You
     * @date 2021/11/27 09:03
     */
    private List<Long> findParentPath(Long catelogId, List<Long> path) {
        path.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), path);
        }
        return path;
    }

    /**
     * 集联更新分类
     *
     * @param category 分类信息
     * @author Clover You
     * @date 2021/11/27 11:00
     */
    @CacheEvict(value = "category", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (StringUtils.hasText(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    /**
     * 查询一级分类
     *
     * @return List<CategoryEntity>
     * @author Clover You
     * @date 2021/12/26 10:41
     */
    @Cacheable(cacheNames = {"category"}, key = "'leve1Category'", sync = true)
    @Override
    public List<CategoryEntity> getLeve1Category() {
        log.info("getLeve1Category调用....");
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("cat_level", 1));
    }

    /**
     * getCatalogJsonLock 锁对象
     */
    private final Object getCatalogJsonLock = new Object();

    /**
     * 查出所有分类，以{"1": {Catalog2Vo}} 的形式返回
     *
     * @return Map<String, Object>
     * @author Clover You
     * @date 2021/12/26 14:50
     */
    @Cacheable(cacheNames = "category", key = "#root.methodName")
    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        return getCatalogJsonFormDB();
    }

    /**
     * 获取所有分类，使用 redis 分布式锁
     * <p>加锁保证原子性，解锁保证原子性</p>
     *
     * @return Map<String, List < Catalog2Vo>>
     * @create 2022-1-2 21:22
     */
    public Map<String, List<Catalog2Vo>> getCatalogJsonWithRedissonLock() throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        // 从缓存中获取数据
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (!StringUtils.hasText(catalogJson)) {
            log.warn("占用分布式锁");
            RLock lock = redisson.getLock("catalogJson-lock");
            lock.lock();
            try {
                // 从缓存中获取数据
                String confirmCache = redisTemplate.opsForValue().get("catalogJson");
                if (!StringUtils.hasText(confirmCache)) {
                    log.warn("从数据库中获取数据");
                    // 从数据库中获取数据
                    Map<String, List<Catalog2Vo>> catalogJsonFormDb = getCatalogJsonFormDB();
                    // 解决缓存穿透

                    if (catalogJsonFormDb == null || catalogJsonFormDb.isEmpty()) {
                        redisTemplate.opsForValue().set("catalogJson", "{}", 1, TimeUnit.DAYS);
                        return new HashMap<>(0);
                    } else {
                        String jsonString = JSON.toJSONString(catalogJsonFormDb);
                        redisTemplate.opsForValue().set("catalogJson", jsonString);
                        return catalogJsonFormDb;
                    }
                } else {
                    catalogJson = confirmCache;
                }
            } finally {
                lock.unlock();
            }
        }
        return JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {});
    }

    /**
     * 获取所有分类，使用 redis 分布式锁
     * <p>加锁保证原子性，解锁保证原子性</p>
     *
     * @return Map<String, List < Catalog2Vo>>
     * @create 2022-1-2 21:22
     */
    public Map<String, List<Catalog2Vo>> getCatalogJsonWithRedisLock() throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        // 从缓存中获取数据
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (!StringUtils.hasText(catalogJson)) {
            log.warn("占用分布式锁");
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            // 占用分布式锁 set EX NX
            Boolean lock = ops.setIfAbsent("product_catalog_lock", uuid, 30, TimeUnit.SECONDS);
            if (Boolean.TRUE.equals(lock)) {
                try {
                    // 从缓存中获取数据
                    String confirmCache = redisTemplate.opsForValue().get("catalogJson");
                    if (!StringUtils.hasText(confirmCache)) {
                        log.warn("从数据库中获取数据");
                        // 从数据库中获取数据
                        Map<String, List<Catalog2Vo>> catalogJsonFormDb = getCatalogJsonFormDB();
                        // 解决缓存穿透
                        if (catalogJsonFormDb == null || catalogJsonFormDb.isEmpty()) {
                            redisTemplate.opsForValue().set("catalogJson", "{}", 1, TimeUnit.DAYS);
                            return new HashMap<>(0);
                        } else {
                            String jsonString = JSON.toJSONString(catalogJsonFormDb);
                            redisTemplate.opsForValue().set("catalogJson", jsonString);
                            return catalogJsonFormDb;
                        }
                    } else {
                        catalogJson = confirmCache;
                    }
                } finally {
                    // 释放锁
                    unlockOfRedis("product_catalog_lock", uuid);
                }

            } else {
                Thread.sleep(100);
                return getCatalogJsonWithRedisLock();
            }
        }
        return JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {});
    }

    /**
     * 删除分布式锁
     *
     * @param lockName 锁名称
     * @param uuid     锁id
     * @Author: Clover You
     * @Date: 2022/1/4 10:55
     **/
    private void unlockOfRedis(String lockName, String uuid) {
        String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1])" + " else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        redisTemplate.execute(redisScript, Collections.singletonList(lockName), uuid);
    }

    /**
     * 查出所有分类，以{"1": {Catalog2Vo}} 的形式返回，使用缓存
     *
     * @return Map<String, List < Catalog2Vo>>
     * @author Clover You
     * @date 2021/12/30 15:36
     */
    public Map<String, List<Catalog2Vo>> getCatalogJsonWithLocalLock() {
        // 从缓存中获取数据
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (!StringUtils.hasText(catalogJson)) {
            synchronized (getCatalogJsonLock) {
                // 从缓存中获取数据
                String confirmCache = redisTemplate.opsForValue().get("catalogJson");
                if (!StringUtils.hasText(confirmCache)) {
                    log.warn("从数据库中获取数据");
                    // 从数据库中获取数据
                    Map<String, List<Catalog2Vo>> catalogJsonFormDb = getCatalogJsonFormDB();
                    // 解决缓存穿透
                    if (catalogJsonFormDb == null || catalogJsonFormDb.isEmpty()) {
                        redisTemplate.opsForValue().set("catalogJson", "{}", 1, TimeUnit.DAYS);
                        return new HashMap<>(0);
                    } else {
                        String jsonString = JSON.toJSONString(catalogJsonFormDb);
                        redisTemplate.opsForValue().set("catalogJson", jsonString);
                        return catalogJsonFormDb;
                    }
                }
            }
        }
        return JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {});
    }

    /**
     * 从数据库中查出所有分类，以{"1": {Catalog2Vo}} 的形式返回
     *
     * @return Map<String, Object>
     * @author Clover You
     * @date 2021/12/26 14:50
     */
    public Map<String, List<Catalog2Vo>> getCatalogJsonFormDB() {
        // 所有分类数据
        List<CategoryEntity> categoryEntitiesAll = baseMapper.selectList(null);
        // 查询所有一级分类
        List<CategoryEntity> leve1Category = getCategoryByParentCid(categoryEntitiesAll, 0L);
        return leve1Category.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {

            List<CategoryEntity> categoryEntities = getCategoryByParentCid(categoryEntitiesAll, v.getCatId());

            List<Catalog2Vo> catalog2Vos = new ArrayList<>();
            if (categoryEntities != null) {
                catalog2Vos = categoryEntities.stream().map(item -> {
                    Catalog2Vo catalog2Vo = new Catalog2Vo(
                        v.getCatId().toString(), null, item.getCatId().toString(), item.getName()
                    );

                    List<CategoryEntity> categoryEntityList = getCategoryByParentCid(
                        categoryEntitiesAll, item.getCatId()
                    );
                    List<Catalog2Vo.Catalog3Vo> level3List = null;
                    if (categoryEntityList != null) {
                        level3List = categoryEntityList.stream().map(level3 -> {
                            return new Catalog2Vo.Catalog3Vo(
                                item.getCatId().toString(),
                                level3.getCatId().toString(),
                                level3.getName()
                            );
                        }).collect(Collectors.toList());
                        catalog2Vo.setCatalog3List(level3List);
                    }
                    return catalog2Vo;
                }).collect(Collectors.toList());
            }
            return catalog2Vos;
        }));
    }

    /**
     * 通过父id在指定列表中查找指定项
     *
     * @param metaList 数据源
     * @return List<CategoryEntity>
     * @author Clover You
     * @date 2021/12/30 10:39
     */
    private List<CategoryEntity> getCategoryByParentCid(List<CategoryEntity> metaList, Long cId) {
        return metaList.stream().filter(item -> item.getParentCid().equals(cId)).collect(Collectors.toList());
    }

}

