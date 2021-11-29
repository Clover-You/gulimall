package top.ctong.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.ctong.gulimall.common.constant.ProductConstant;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;
import top.ctong.gulimall.product.dao.AttrAttrgroupRelationDao;
import top.ctong.gulimall.product.dao.AttrDao;
import top.ctong.gulimall.product.dao.AttrGroupDao;
import top.ctong.gulimall.product.dao.CategoryDao;
import top.ctong.gulimall.product.entity.AttrAttrgroupRelationEntity;
import top.ctong.gulimall.product.entity.AttrEntity;
import top.ctong.gulimall.product.entity.AttrGroupEntity;
import top.ctong.gulimall.product.entity.CategoryEntity;
import top.ctong.gulimall.product.service.AttrService;
import top.ctong.gulimall.product.service.CategoryService;
import top.ctong.gulimall.product.vo.AttrGroupRelationVo;
import top.ctong.gulimall.product.vo.AttrRespVo;
import top.ctong.gulimall.product.vo.AttrVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
 * 商品属性
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * 保存属性
     * @param attr 属性信息
     * @author Clover You
     * @date 2021/11/27 15:21
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity entity = new AttrEntity();
        BeanUtils.copyProperties(attr, entity);
        // 保存基本数据
        this.save(entity);
        // 保存关联关系, 基本属性不需要新增关联关系
        if (attr.getAttrType() == ProductConstant.AttrEnum.BASE.getCode() && attr.getAttrId() != null) {
            AttrAttrgroupRelationEntity group = new AttrAttrgroupRelationEntity();
            group.setAttrGroupId(attr.getAttrGroupId());
            group.setAttrId(entity.getAttrId());
            attrAttrgroupRelationDao.insert(group);
        }
    }

    /**
     * 查询属性基础信息
     * @param params 自定义查询参数
     * @param catelogId 分类id
     * @param attrType 属性类型
     * @return PageUtils
     * @author Clover You
     * @date 2021/11/27 15:55
     */
    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_type", "base".equalsIgnoreCase(attrType) ?
                ProductConstant.AttrEnum.BASE.getCode() :
                ProductConstant.AttrEnum.SALE.getCode()
        );
        if (catelogId != 0) {
            wrapper.eq("catelog_id", catelogId);
        }
        String key = (String) params.get("key");
        if (StringUtils.hasText(key)) {
            wrapper.and(csm -> {
                csm.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );
        List<AttrEntity> records = page.getRecords();
        // 获取分类名和分组名
        Stream<AttrRespVo> stream = records.stream().map(attr -> {
            AttrRespVo resp = new AttrRespVo();
            BeanUtils.copyProperties(attr, resp);

            // 如果是基本属性才查关联分组信息
            if (resp.getAttrType() == ProductConstant.AttrEnum.BASE.getCode() && attr.getAttrId() != null) {
                // select * from pms_attr_attrgroup_relation as re where re.attr_id = ?
                AttrAttrgroupRelationEntity attrId = attrAttrgroupRelationDao.selectOne(
                        new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId())
                );

                if (attrId != null) {
                    // 获取分组名称
                    Long attrGroupId = attrId.getAttrGroupId();
                    if (attrGroupId != null) {
                        AttrGroupEntity group = attrGroupDao.selectById(attrGroupId);
                        if (group != null) {
                            resp.setGroupName(group.getAttrGroupName());
                        }
                    }
                }
            }

            // 获取分类名称
            Long attrCatelogId = attr.getCatelogId();
            if (attrCatelogId != null) {
                CategoryEntity category = categoryDao.selectById(attrCatelogId);
                if (category != null) {
                    resp.setCatelogName(category.getName());
                }
            }
            return resp;
        });
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(stream.collect(Collectors.toList()));
        return pageUtils;
    }

    /**
     * 根据属性id查询属性信息
     * @param attrId 属性id
     * @return AttrRespVo
     * @author Clover You
     * @date 2021/11/27 16:57
     */
    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity byId = this.getById(attrId);
        AttrRespVo respVo = new AttrRespVo();
        BeanUtils.copyProperties(byId, respVo);
        // 获取分组信息
        if (respVo.getAttrType() == ProductConstant.AttrEnum.BASE.getCode()) {
            // select * from pms_attr_attrgroup_relation where attr_id =?
            AttrAttrgroupRelationEntity groupRelation = attrAttrgroupRelationDao.selectOne(
                    new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId)
            );

            if (groupRelation != null) {
                AttrGroupEntity groupEntity = attrGroupDao.selectById(groupRelation.getAttrGroupId());
                if (groupEntity != null) {
                    respVo.setGroupName(groupEntity.getAttrGroupName());
                }
                respVo.setAttrGroupId(groupRelation.getAttrGroupId());
            }
        }

        // 设置分类信息
        Long[] categoryPath = categoryService.findCategoryPath(byId.getCatelogId());
        respVo.setCatelogPath(categoryPath);

        CategoryEntity categoryEntity = categoryDao.selectById(byId.getCatelogId());
        if (categoryEntity != null) {
            respVo.setCatelogName(categoryEntity.getName());
        }

        return respVo;
    }

    /**
     * 保存属性修改后的信息
     * @param attr 属性信息
     * @author Clover You
     * @date 2021/11/28 10:32
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        // 先保存修改后的属性信息
        this.updateById(attrEntity);

        if (attr.getAttrType() == ProductConstant.AttrEnum.BASE.getCode()) {
            Long attrGroupId = attr.getAttrGroupId();
            if (attrGroupId != null) {
                QueryWrapper<AttrAttrgroupRelationEntity> updateWrapper = new QueryWrapper<>();
                updateWrapper.eq("attr_id", attr.getAttrId());

                AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
                relationEntity.setAttrGroupId(attrGroupId);
                relationEntity.setAttrId(attr.getAttrId());
                // 检查当前数据库中是否有对应属性关联信息
                Long count = attrAttrgroupRelationDao.selectCount(updateWrapper);
                // 如果大于0代表有对应关联信息
                if (count > 0) {
                    attrAttrgroupRelationDao.update(relationEntity, updateWrapper);
                } else {
                    // 不存在关联信息时新增
                    attrAttrgroupRelationDao.insert(relationEntity);
                }
            }
        }
    }

    /**
     * 通过属性分组查询所有属性信息
     * @param attrGroupId 属性分组id
     * @return List<AttrEntity>
     * @author Clover You
     * @date 2021/11/28 15:09
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {
        QueryWrapper<AttrAttrgroupRelationEntity> relationWrapper = new QueryWrapper<>();
        relationWrapper.eq("attr_group_id", attrGroupId);
        // 查询属性和属性分组关联信息
        List<AttrAttrgroupRelationEntity> groupRelationEntity = attrAttrgroupRelationDao.selectList(relationWrapper);
        if (groupRelationEntity.isEmpty()) {
            return new ArrayList<>();
        }
        // 收集属性分组id
        List<Long> attrIds = groupRelationEntity.stream().map(attr -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());
        return this.listByIds(attrIds);
    }

    /**
     * 删除属性与属性分组关联信息
     * @param vos 关联信息列表
     * @author Clover You
     * @date 2021/11/28 19:09
     */
    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        if (vos.length > 0) {
            List<AttrAttrgroupRelationEntity> list = Arrays.stream(vos).map(vo -> {
                AttrAttrgroupRelationEntity entity = new AttrAttrgroupRelationEntity();
                BeanUtils.copyProperties(vo, entity);
                return entity;
            }).collect(Collectors.toList());
            attrAttrgroupRelationDao.deleteBatchRelation(list);
        }
    }

    /**
     * 根据指定分组id获取未关联本分组的所有属性「分页」
     * @param params 其他参数
     * @param attrGroupId 分组id
     * @return PageUtils
     * @author Clover You
     * @date 2021/11/28 20:02
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrGroupId) {
        // 当前分组只能关联自己所属分类的所有属性
        AttrGroupEntity currentGroup = attrGroupDao.selectById(attrGroupId);
        Long catelogId = currentGroup.getCatelogId();
        // 查找当前分类下的所有分组
        List<AttrGroupEntity> attrGroupAll = attrGroupDao.selectList(
                new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId)
        );
        // 当前分组只能关联别的分组没有引用的属性
        // 获取这些分组关联的属性
        List<Long> groupIds = attrGroupAll.stream().map(group -> {
            return group.getAttrGroupId();
        }).collect(Collectors.toList());

        List<AttrAttrgroupRelationEntity> relationEntities = new ArrayList<>();
        if (!groupIds.isEmpty()) {
            relationEntities = attrAttrgroupRelationDao.selectList(
                    new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupIds)
            );
        }

        List<Long> attrIds = relationEntities.stream().map(relation -> {
            return relation.getAttrId();
        }).collect(Collectors.toList());
        // 查找不再attrIds列表中的属性
        QueryWrapper<AttrEntity> attrWrapper = new QueryWrapper<>();
        attrWrapper.eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.BASE.getCode());
        if (!attrIds.isEmpty()) {
            attrWrapper.notIn("attr_id", attrIds);
        }

        // 可能有模糊查询
        String key = (String) params.get("key");
        if (StringUtils.hasText(key)) {
            attrWrapper.and(csm -> {
                csm.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), attrWrapper);


        return new PageUtils(page);
    }
}