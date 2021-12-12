package top.ctong.gulimall.product.service.impl;

import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.product.dao.AttrGroupDao;
import top.ctong.gulimall.product.entity.AttrEntity;
import top.ctong.gulimall.product.entity.AttrGroupEntity;
import top.ctong.gulimall.product.service.AttrGroupService;
import top.ctong.gulimall.product.service.AttrService;
import top.ctong.gulimall.product.vo.AttrGroupWithAttrsVo;


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
 * 属性分组
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 通过分类id分页查询分组信息
     * @param params 自定义查询规则
     * @param catalogId 分类id
     * @return PageUtils
     * @author Clover You
     * @date 2021/11/25 16:31
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catalogId) {
        IPage<AttrGroupEntity> page;
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");

        if (!StringUtils.isEmpty(key)) {
            wrapper.and(csm -> {
                csm.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }

        if (catalogId == 0) {
            page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
        } else {
            // select * from pms_attr_group where catelog_id=? and (attr_group_id=? or attr_group_name)
            wrapper.eq("catelog_id", catalogId);
            page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
        }
        return new PageUtils(page);
    }

    /**
     * 获取分类下所有分组&关联属性
     * @param catelogId 分类Id
     * @return List<AttrGroupWithAttrsVo>
     * @author Clover You
     * @date 2021/12/7 11:03
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        QueryWrapper<AttrGroupEntity> attrGroupQueryWrapper = new QueryWrapper<>();
        attrGroupQueryWrapper.eq("catelog_id", catelogId);
        List<AttrGroupEntity> attrGroupResult = this.list(attrGroupQueryWrapper);
        return Optional.of(attrGroupResult).orElse(new ArrayList<>())
                .stream().map(item -> {
                    List<AttrEntity> attrs = attrService.getRelationAttr(item.getAttrGroupId());
                    AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
                    BeanUtils.copyProperties(item, attrGroupWithAttrsVo);
                    attrGroupWithAttrsVo.setAttrs(attrs);
                    return attrGroupWithAttrsVo;
                }).collect(Collectors.toList());
    }

}