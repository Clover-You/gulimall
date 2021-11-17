package top.ctong.gulimall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.ctong.gulimall.common.utils.R;

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
 * coupon远程调用接口
 * </p>
 * <p>
 * [@FeignClient] 告诉spring cloud这个接口是个远程客户端
 * @author Clover You
 * @create 2021-11-17 09:00
 */
@FeignClient("gulimall-coupon")
@RequestMapping("/coupon/coupon")
public interface CouponFeignService {

    /**
     * 通过自定义参数查询列表
     * @param params 自定义参数
     * @return R
     * @author Clover You
     * @date 2021/11/17 09:11
     */
    @RequestMapping("/list")
    R list(@RequestParam Map<String, Object> params);
}
