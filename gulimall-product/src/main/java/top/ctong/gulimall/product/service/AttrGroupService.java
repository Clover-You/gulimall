package top.ctong.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.product.entity.AttrGroupEntity;
import top.ctong.gulimall.product.vo.AttrGroupWithAttrsVo;
import top.ctong.gulimall.product.vo.SkuItemVo;

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
 * 属性分组
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    /**
     * 分页查询分组信息
     * @param params 自定义规则
     * @return PageUtils
     * @author Clover You
     * @date 2021/11/25 16:32
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 通过分类id分页查询分组信息
     * @param params 自定义查询规则
     * @param catalogId 分类id
     * @return PageUtils
     * @author Clover You
     * @date 2021/11/25 16:31
     */
    PageUtils queryPage(Map<String, Object> params, Long catalogId);

    /** 
     * 获取分类下所有分组&关联属性
     * @param catelogId 分类Id
     * @return List<AttrGroupWithAttrsVo>
     * @author Clover You
     * @date 2021/12/7 11:03
     */
    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);

    /**
     * 查处但前spu对应的所有属性分组信息以及但前分组下的所有属性对应的值
     *
     * @param spuId spu id
     * @param catelogId 三级分类id
     * @return List<SpuItemBaseAttrVo>
     * @author Clover You
     * @date
     */
    List<SkuItemVo.SpuItemBaseAttrVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catelogId);
}

