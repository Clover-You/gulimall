package top.ctong.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;
import top.ctong.gulimall.product.dao.AttrAttrgroupRelationDao;
import top.ctong.gulimall.product.dao.AttrDao;
import top.ctong.gulimall.product.entity.AttrAttrgroupRelationEntity;
import top.ctong.gulimall.product.entity.AttrEntity;
import top.ctong.gulimall.product.entity.AttrGroupEntity;
import top.ctong.gulimall.product.entity.CategoryEntity;
import top.ctong.gulimall.product.service.AttrGroupService;
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
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
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
        // 保存关联关系
        AttrAttrgroupRelationEntity group = new AttrAttrgroupRelationEntity();
        group.setAttrGroupId(attr.getAttrGroupId());
        group.setAttrId(entity.getAttrId());
        attrAttrgroupRelationDao.insert(group);
    }

    /**
     * 查询属性基础信息
     * @param params 自定义查询参数
     * @param catelogId 分类id
     * @return PageUtils
     * @author Clover You
     * @date 2021/11/27 15:55
     */
    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
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

            // select * from pms_attr_attrgroup_relation as re where re.attr_id = ?
            AttrAttrgroupRelationEntity attrId = attrAttrgroupRelationDao.selectOne(
                    new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId())
            );

            if (attrId != null) {
                // 获取分组名称
                Long attrGroupId = attrId.getAttrGroupId();
                AttrGroupEntity group = attrGroupService.getById(attrGroupId);
                resp.setGroupName(group.getAttrGroupName());
                // 获取分类名称
                Long attrCatelogId = attr.getCatelogId();
                CategoryEntity category = categoryService.getById(attrCatelogId);
                resp.setCatelogName(category.getName());
            }
            return resp;
        });
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(stream.collect(Collectors.toList()));
        return pageUtils;
    }

}