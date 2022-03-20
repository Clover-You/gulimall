package top.ctong.gulimall.product.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.ctong.gulimall.common.exception.BizCodeEnum;
import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.product.feign.SeckillFeignService;

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
 * 秒杀服务降级回调
 * </p>
 * @author Clover You
 * @email cloveryou02@163.com
 * @create 2022-03-20 4:04 下午
 */
@Slf4j
@Component
public class SeckillFeignServiceFallBack implements SeckillFeignService {
    /**
     * 通过商品id查询当前商品是否参与秒杀活动
     * @param skuId 商品id
     * @return R
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/16 9:25 上午
     */
    @Override
    public R getSkuSeckillInfo(Long skuId) {
        log.info("触发熔断保护...");
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION);
    }
}
