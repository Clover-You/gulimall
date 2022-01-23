package top.ctong.gulimall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
 * Copyright 2021 Clover You.
 * <p>
 * 商品服务远程调用
 * </p>
 *
 * @author Clover You
 * @create 2021-12-14 11:05
 */
@FeignClient("gulimall-product")
@RequestMapping("/product")
public interface ProductFeignService {

    /**
     * 通过sku id远程查询sku信息
     *
     * @param skuId sku id
     * @return R
     * @author Clover You
     * @date 2021/12/14 11:07
     */
    @GetMapping("/skuinfo/info/{skuId}")
    R skuInfo(@PathVariable("skuId") Long skuId);

    /**
     * 远程查询attr信息
     *
     * @param attrId attr id
     * @return R
     * @author Clover You
     * @date 2022/1/23 17:25
     */
    @GetMapping("/attr/info/{attrId}")
    R attrInfo(@PathVariable("attrId") Long attrId);

    /**
     * 通过多个品牌id查询指定品牌
     *
     * @param brandIds 品牌id
     * @return R
     * @author Clover You
     * @date 2022/1/23 19:16
     */
    @RequestMapping("/brand/infos")
    R brandInfo(@RequestParam("brandIds") List<Long> brandIds);

}
