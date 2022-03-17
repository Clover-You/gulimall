package top.ctong.gulimall.seckill.service;

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
 * 秒杀服务
 * </p>
 * @author Clover You
 * @email cloveryou02@163.com
 * @create 2022-03-14 6:37 下午
 */
public interface SeckillService {

    /**
     * 上架最近三天秒杀商品
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/14 6:40 下午
     */
    void uploadSeckillSkuLatest3Days();

    /**
     * 获取当前能参与秒杀活动的商品的信息
     * @return List<SeckillSkuRedisTo>
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/15 3:39 下午
     */
    List<SeckillSkuRedisTo> getCurrentSeckiilSkus();

    /**
     * 通过商品id查询当前商品是否参与秒杀活动
     * @param skuId 商品id
     * @return SeckillSkuRedisTo
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/16 9:27 上午
     */
    SeckillSkuRedisTo getSkuSeckillInfo(Long skuId);

    /**
     * 秒杀商品
     * @param killId 秒杀信息
     * @param key 随机码
     * @param num 数量
     * @return String
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/16 4:37 下午
     */
    String kill(String killId, String key, Integer num);
}
