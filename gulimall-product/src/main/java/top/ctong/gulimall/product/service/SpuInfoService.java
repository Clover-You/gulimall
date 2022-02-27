package top.ctong.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.product.entity.SpuInfoDescEntity;
import top.ctong.gulimall.product.entity.SpuInfoEntity;
import top.ctong.gulimall.product.vo.SpuSaveVo;

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
 * spu信息
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存spu信息
     * @param vo spu信息
     * @author Clover You
     * @date 2021/12/9 10:08
     */
    void saveSpuInfo(SpuSaveVo vo);

    /**
     * 保存SPU基本信息
     * @param spuInfo spu信息
     * @author Clover You
     * @date 2021/12/9 10:36
     */
    void saveBaseSpuInfo(SpuInfoEntity spuInfo);

    /**
     * 根据条件查询SPU信息
     * @param params 自定义条件
     * @return PageUtils
     * @author Clover You
     * @date 2021/12/12 08:28
     */
    PageUtils queryPageByCondition(Map<String, Object> params);

    /**
     * 商品上架
     * @param spuId 规格id
     * @author Clover You
     * @date 2021/12/22 09:17
     */
    void up(Long spuId);

    /**
     * 根据skuId 查询 spu信息
     * @param skuId 商品id
     * @return SpuInfoEntity
     * @author Clover You
     * @date 2022/2/27 3:17 下午
     */
    SpuInfoEntity getSpuInfoBySkuId(Long skuId);
}

