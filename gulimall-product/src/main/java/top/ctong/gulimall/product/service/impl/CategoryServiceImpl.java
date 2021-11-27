package top.ctong.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;
import top.ctong.gulimall.product.dao.CategoryDao;
import top.ctong.gulimall.product.entity.CategoryEntity;
import top.ctong.gulimall.product.service.CategoryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    /**
     * 查询叶
     * @param params 自定义查询条件
     * @return PageUtils
     * @author Clover You
     * @date 2021/11/21 20:36
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询所有分类并构造树形结构
     * @return List<CategoryEntity>
     * @author Clover You
     * @date 2021/11/21 20:36
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        // TODO 查出所有分类
        // 没有查询条件selectList可为null
        List<CategoryEntity> entities = baseMapper.selectList(null);
        // TODO 组装成树形结构
        // 找到所有一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter((category) ->
                category.getParentCid() == 0
        ).map(menu -> {
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
     * @param asList id列表
     * @author Clover You
     * @date 2021/11/22 14:48
     */
    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO 检查当前菜单是否有子菜单
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * 找到指定菜单的子菜单
     * @param root 当前指定菜单
     * @param all 全部菜单
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
     * @param catelogId 要查找的子孙节点id
     * @param path 节点集合
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

}