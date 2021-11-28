package top.ctong.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.product.entity.AttrEntity;
import top.ctong.gulimall.product.vo.AttrRespVo;
import top.ctong.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;


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
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存属性
     * @param attr 属性信息
     * @author Clover You
     * @date 2021/11/27 15:21
     */
    void saveAttr(AttrVo attr);

    /**
     * 查询属性基础信息
     * @param params 自定义查询参数
     * @param catelogId 分类id
     * @param attrType 属性类型
     * @return PageUtils
     * @author Clover You
     * @date 2021/11/27 15:55
     */
    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    /**
     * 根据属性id查询属性信息
     * @param attrId 属性id
     * @return AttrRespVo
     * @author Clover You
     * @date 2021/11/27 16:57
     */
    AttrRespVo getAttrInfo(Long attrId);

    /**
     * 保存属性修改后的信息
     * @param attr 属性信息
     * @author Clover You
     * @date 2021/11/28 10:32
     */
    void updateAttr(AttrVo attr);

    /** 
     * 通过属性分组查询所有属性信息
     * @param attrGroupId 属性分组id
     * @return List<AttrEntity>
     * @author Clover You
     * @date 2021/11/28 15:09
     */
    List<AttrEntity> getRelationAttr(Long attrGroupId);
}

