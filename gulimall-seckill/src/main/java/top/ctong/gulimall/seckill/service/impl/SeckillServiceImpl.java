package top.ctong.gulimall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.seckill.feign.CouponFeignService;
import top.ctong.gulimall.seckill.feign.ProductFeignService;
import top.ctong.gulimall.seckill.service.SeckillService;
import top.ctong.gulimall.seckill.to.SeckillSessionTo;
import top.ctong.gulimall.seckill.to.SeckillSkuRedisTo;
import top.ctong.gulimall.seckill.to.SeckillSkuRelationTo;
import top.ctong.gulimall.seckill.vo.SkuInfoVo;

import java.util.*;
import java.util.stream.Collectors;

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
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    /**
     * 秒杀商品的库存
     */
    private static final String SKU_STOCK_SEMAPHORE = "seckill:stock:";

    /**
     * 场次信息
     */
    private static final String SESSION_CACHE_PREFIX = "seckill:sessions:";

    /**
     * 参与秒杀的商品
     */
    private static final String SECKILL_SKUS_CACHE_PREFIX = "seckill:skus";

    /**
     * 上架最近三天秒杀商品
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/14 6:40 下午
     */
    @Override
    public void uploadSeckillSkuLatest3Days() {
        R rResult = couponFeignService.getLates3DaySession();
        if (!rResult.getCode().equals(0)) {
            log.error("远程错误！！！");
            return;
        }

        List<SeckillSessionTo> data = rResult.getData(new TypeReference<List<SeckillSessionTo>>() {
        });

        // 缓存秒杀信息
        saveSessionInfos(data);
        // 缓存商品信息
        saveSessionSkuInfos(data);
    }

    /**
     * 保存活动商品信息
     * @param data 活动信息
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/14 8:10 下午
     */
    private void saveSessionSkuInfos(List<SeckillSessionTo> data) {
        data.stream().forEach(session -> {
            BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(
                SECKILL_SKUS_CACHE_PREFIX
            );
            List<SeckillSkuRelationTo> relations = session.getRelation();
            relations.stream().forEach(relation -> {
                String targetKey = relation.getPromotionSessionId() + "_" + relation.getSkuId().toString();
                // 解决幂等性
                Boolean hasKey = ops.hasKey(targetKey);
                if (Boolean.TRUE.equals(hasKey)) {
                    return;
                }

                SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
                // 获取 SKU 基本数据
                R skuR = productFeignService.getSkuInfoById(relation.getSkuId());
                if (skuR.getCode() != 0) {
                    return;
                }
                SkuInfoVo skuInfo = skuR.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                });
                redisTo.setSkuInfo(skuInfo);

                // 秒杀信息
                BeanUtils.copyProperties(relation, redisTo);

                // 设置开始和结束时间、随机码
                skuInfo.setStartTime(session.getStartTime().getTime());
                skuInfo.setEndTime(session.getEndTime().getTime());

                // 设置随机码，在商品活动开始前开始设置，避免秒杀接口暴露后随意下单
                String randomToken = UUID.randomUUID().toString().replace("-", "");
                redisTo.setRandomCode(randomToken);

                String jsonStr = JSONObject.toJSONString(redisTo);
                ops.put(targetKey, jsonStr);

                // 设置一个分布式信号量，用于断流缓解数据库压力(限流)
                RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + randomToken);
                semaphore.trySetPermits(relation.getSeckillCount());
            });
        });
    }

    /**
     * 保存活动信息
     * @param data 活动信息
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/14 8:09 下午
     */
    private void saveSessionInfos(List<SeckillSessionTo> data) {
        data.forEach(session -> {
            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();
            String redisKey = SESSION_CACHE_PREFIX + startTime + "_" + endTime;

            Boolean hasKey = stringRedisTemplate.hasKey(redisKey);
            if (Boolean.TRUE.equals(hasKey)) {
                return;
            }

            // 收集所有商品id
            List<String> ids = session.getRelation().stream()
                .map((item) ->
                    item.getPromotionSessionId() + "_" + item.getSkuId().toString()
                ).collect(Collectors.toList());
            // 缓存活动id
            if (ids.isEmpty()) {
                return;
            }
            stringRedisTemplate.opsForList().leftPushAll(redisKey, ids);
        });
    }

    /**
     * 获取当前能参与秒杀活动的商品的信息
     * @return List<SeckillSkuRedisTo>
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/15 3:39 下午
     */
    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckiilSkus() {
        // 获取当前时间属于哪个秒杀场次
        long time = System.currentTimeMillis();
        Set<String> keys = redisTemplate.keys(SESSION_CACHE_PREFIX + "*");
        if (keys == null) {
            return new ArrayList<>(0);
        }
        // 只获取符合条件的一个
        for (String key : keys) {
            String[] times = key.replace(SESSION_CACHE_PREFIX, "").split("_");
            long startTime = Long.parseLong(times[0]);
            long endTime = Long.parseLong(times[1]);

            if (time >= startTime && time <= endTime) {
                // 获取到该时间段所有的场次id
                List<String> ele = stringRedisTemplate.opsForList().range(key, -100, 100);
                if (ele == null || ele.isEmpty()) {
                    break;
                }

                BoundHashOperations<String, String, String> ops = stringRedisTemplate.boundHashOps(
                    SECKILL_SKUS_CACHE_PREFIX
                );
                // 批量获取参与秒杀活动的商品根据当前场次
                List<String> stringsSeckillSkuRedisList = ops.multiGet(ele);
                if (stringsSeckillSkuRedisList == null) {
                    break;
                }

                return stringsSeckillSkuRedisList.stream().map((stringsSeckillSkuRedis) -> {
                    return JSON.parseObject(stringsSeckillSkuRedis, SeckillSkuRedisTo.class);
                }).collect(Collectors.toList());
            }
        }

        return new ArrayList<>(0);
    }

}
