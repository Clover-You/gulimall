package top.ctong.gulimall.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.ctong.gulimall.common.utils.R;

import java.math.BigDecimal;
import java.util.List;

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
 * Copyright 2022 Clover You.
 * <p>
 * 商品远程服务
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-18 6:27 下午
 */
@FeignClient("gulimall-product")
public interface ProductFeignServer {

    /**
     * 根据sku id远程查询sku信息
     * @param skuId 商品规格id
     * @return R
     * @author Clover You
     * @date 2022/2/18 6:30 下午
     */
    @GetMapping("/product/skuinfo/info/{skuId}")
    R getSkuInfoBySkuId(@PathVariable("skuId") Long skuId);

    /**
     * 根据指定商品规格查询商品销售属性信息
     * @param skuId 商品规格id
     * @return List<String>
     * @author Clover You
     * @date 2022/2/18 7:05 下午
     */
    @RequestMapping("/product/skusaleattrvalue/string-list/{skuId}")
    List<String> getSkuSaleAttrValues(@PathVariable("skuId") Long skuId);

    /**
     * 通过商品id查询价格
     * @param skuId 商品id
     * @return BigDecimal
     * @author Clover You
     * @date 2022/2/25 2:56 下午
     */
    @GetMapping("/product/skuinfo/{skuId}/price")
    BigDecimal getPriceBySkuId(@PathVariable("skuId") Long skuId);
}
