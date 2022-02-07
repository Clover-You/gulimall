package top.ctong.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.product.entity.SkuInfoEntity;
import top.ctong.gulimall.product.vo.SkuItemVo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


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
 * sku信息
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存sku信息
     * @param skuInfoEntity sku信息
     * @author Clover You
     * @date 2021/12/9 16:32
     */
    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    /**
     * 根据自定义条件通过sku信息查询商品信息
     * @param params 自定义条件
     * @return PageUtils
     * @author Clover You
     * @date 2021/12/12 09:03
     */
    PageUtils queryPageByCondition(Map<String, Object> params);

    /**
     * 通过规格id查询所有属性信息
     * @param spuId 规格id
     * @return List<SkuInfoEntity>
     * @author Clover You
     * @date 2021/12/22 09:45
     */
    List<SkuInfoEntity> getSkusBySpuId(Long spuId);

    /**
     * 通过id查询sku详细信息
     * @param skuId sku id
     * @return SkuItemVo
     * @author Clover You
     * @date 2022/2/4 8:03 下午
     */
    SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException;
}

