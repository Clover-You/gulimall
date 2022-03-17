package top.ctong.gulimall.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.seckill.service.SeckillService;
import top.ctong.gulimall.seckill.to.SeckillSkuRedisTo;

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
 * 秒杀服务前端控制器
 * </p>
 * @author Clover You
 * @email cloveryou02@163.com
 * @create 2022-03-15 3:13 下午
 */
@RestController
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 获取当前可以参与秒杀活动的商品
     * @return R
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/15 3:15 下午
     */
    @GetMapping("/current-seckiil-skus")
    public R getCurrentSeckiilSkus() {
        List<SeckillSkuRedisTo> list = seckillService.getCurrentSeckiilSkus();
        return R.ok().setData(list);
    }

    /**
     * 通过商品id查询当前商品是否参与秒杀活动
     * @param skuId 商品id
     * @return R
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/16 9:25 上午
     */
    @GetMapping("/sku/seckill/{skuId}")
    public R getSkuSeckillInfo(@PathVariable("skuId") Long skuId) {
        SeckillSkuRedisTo data = seckillService.getSkuSeckillInfo(skuId);
        return R.ok().setData(data);
    }

    /**
     * 秒杀商品
     * @param killId 秒杀id
     * @param key 随机码
     * @param num 秒杀数量
     * @return R
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/16 4:00 下午
     */
    @GetMapping("/kill")
    public R secKill(@RequestParam("killId") String killId,
                     @RequestParam("key") String key,
                     @RequestParam("num") Integer num) {
        String orderSn = seckillService.kill(killId, key, num);
        return R.ok();

    }

}
