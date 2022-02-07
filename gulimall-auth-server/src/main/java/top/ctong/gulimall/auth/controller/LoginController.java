package top.ctong.gulimall.auth.controller;

import com.alibaba.nacos.common.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.ctong.gulimall.common.constant.AuthServerConstant;
import top.ctong.gulimall.common.exception.BizCodeEnum;
import top.ctong.gulimall.common.feign.ThirdPartyFeignService;
import top.ctong.gulimall.common.utils.R;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
 * 前端登录控制器
 * </p>
 *
 * @author Clover You
 * @create 2022-02-07 9:18 下午
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * redis 缓存验证码与设置时间的分隔符：123456_2021...
     */
    private final String REDIS_CACHE_CODE_SEPARATOR = "_";

    /**
     * 短信验证码重试间隔时间
     */
    private final long SMS_CODE_CACHE_RETRY_INTERVAL_TIME = 60000L;

    /**
     * 用户注册-发送手机短信验证码
     *
     * @param phone 手机号
     * @return R
     * @author Clover You
     * @date 2022/2/7 10:05 下午
     */
    @GetMapping("/send/sms")
    public R sendSmsCode(@RequestParam("phone") String phone) {
        String redisCacheKey = AuthServerConstant.REG_SMS_CODE_CACHE_PREFIX + phone;

        // 检查redis中是否存在验证码
        String cacheData = redisTemplate.opsForValue().get(redisCacheKey);
        // TODO 接口防刷
        if (StringUtils.hasText(cacheData)) {
            String[] split = cacheData.split(REDIS_CACHE_CODE_SEPARATOR);
            long alive = System.currentTimeMillis() - Long.parseLong(split[1]);
            // 间隔时间不能小于 SMS_CODE_CACHE_RETRY_INTERVAL_TIME 指定时间
            if (alive < SMS_CODE_CACHE_RETRY_INTERVAL_TIME) {
                return R.error(
                        BizCodeEnum.VALID_SMS_CODE_EXCEPTION.getCode(),
                        BizCodeEnum.VALID_SMS_CODE_EXCEPTION.getMsg()
                ).setData(SMS_CODE_CACHE_RETRY_INTERVAL_TIME - alive);
            }
        }
        // 生成验证码
        String ranCode = UUID.randomUUID().toString().substring(0, 6);
        // 发送验证码
        thirdPartyFeignService.sendCode(phone, ranCode);
        // 将验证码缓存到 redis
        ValueOperations<String, String> redisOps = redisTemplate.opsForValue();
        redisOps.set(redisCacheKey, ranCode + "_" + System.currentTimeMillis(), 30, TimeUnit.MINUTES);
        return R.ok();
    }

}
