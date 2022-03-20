package top.ctong.gulimall.seckill.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.ctong.gulimall.common.exception.BizCodeEnum;
import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.seckill.config.SentinelConfig;
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
@Slf4j
@Controller
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
    @ResponseBody
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
    @ResponseBody
    public R getSkuSeckillInfo(@PathVariable("skuId") Long skuId) throws InterruptedException {
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
    public String secKill(@RequestParam("killId") String killId,
                          @RequestParam("key") String key,
                          @RequestParam("num") Integer num,
                          Model model) {
        String orderSn = seckillService.kill(killId, key, num);
        model.addAttribute("orderSn", orderSn);
        return "success";

    }

}
