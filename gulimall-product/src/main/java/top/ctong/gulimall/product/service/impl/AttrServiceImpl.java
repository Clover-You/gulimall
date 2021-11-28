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
import top.ctong.gulimall.product.entity.*;
import top.ctong.gulimall.product.service.AttrService;
import top.ctong.gulimall.product.service.CategoryService;
import top.ctong.gulimall.product.vo.AttrRespVo;
import top.ctong.gulimall.product.vo.AttrVo;

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
        if (attr.getAttrType() == ProductConstant.AttrEnum.BASE.getCode()) {
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
            if (resp.getAttrType() == ProductConstant.AttrEnum.BASE.getCode()) {
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

}