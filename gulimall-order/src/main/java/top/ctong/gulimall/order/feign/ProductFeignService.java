package top.ctong.gulimall.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.ctong.gulimall.common.utils.R;

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
 * @create 2022-02-26 9:38 上午
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {
    /**
     * 根据SkuId查询spu信息
     * @param skuId 商品id
     * @return R
     * @author Clover You
     * @date 2022/2/27 3:16 下午
     */
    @PostMapping("/product/spuinfo/select/{skuId}")
    R getSpuInfoBySkuId(@PathVariable("skuId") Long skuId);

    /** 
     * 根据品牌id获取品牌信息
     * @param brandId 品牌id
     * @return R 
     * @author Clover You 
     * @date 2022/2/27 3:29 下午
     */
    @RequestMapping("/product/brand/info/{brandId}")
    R getBrandInfo(@PathVariable("brandId") Long brandId);
}
