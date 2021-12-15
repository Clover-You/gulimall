package top.ctong.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.product.entity.ProductAttrValueEntity;

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
 * spu属性值
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存SPU基本属性
     * @param attrValueEntityList 属性信息
     * @author Clover You
     * @date 2021/12/9 11:10
     */
    void saveProductAttr(List<ProductAttrValueEntity> attrValueEntityList);

    /** 
     * 获取spu规格
     * @param spuId spu id
     * @return List<ProductAttrValueEntity>
     * @author Clover You
     * @date 2021/12/15 10:41
     */
    List<ProductAttrValueEntity> baseAttrListForSpu(Long spuId);

    /** 
     * 修改规格属性数据
     * @param spuId 规格id
     * @param entityList 属性列表
     * @author Clover You
     * @date 2021/12/15 14:43
     */
    void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> entityList);
}

