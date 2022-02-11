package top.ctong.gulimall.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.ctong.gulimall.auth.feign.MemberServerFeign;
import top.ctong.gulimall.auth.vo.UserLoginVo;
import top.ctong.gulimall.auth.vo.UserRegisterVo;
import top.ctong.gulimall.common.constant.AuthServerConstant;
import top.ctong.gulimall.common.exception.BizCodeEnum;
import top.ctong.gulimall.common.feign.ThirdPartyFeignService;
import top.ctong.gulimall.common.utils.R;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
 * 前端登录控制器
 * </p>
 * @author Clover You
 * @create 2022-02-07 9:18 下午
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MemberServerFeign memberServerFeign;

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
     * @param phone 手机号
     * @return R
     * @author Clover You
     * @date 2022/2/7 10:05 下午
     */
    @ResponseBody
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
        Random random = new Random();
        String ranCode = Integer.toString(random.nextInt(9999));
        // 发送验证码
        thirdPartyFeignService.sendCode(phone, ranCode);
        // 将验证码缓存到 redis
        ValueOperations<String, String> redisOps = redisTemplate.opsForValue();
        redisOps.set(redisCacheKey, ranCode + "_" + System.currentTimeMillis(), 30, TimeUnit.MINUTES);
        return R.ok();
    }

    /**
     * @param userRegister 用户注册信息
     * @param result 注册信息验证 jsr303 valid 封装错误结果集
     * @param redirectAttributes spring 的自定义域，可用于重定向时获取数据
     * @return String
     * @author Clover You
     * @date 2022/2/11 3:28 上午
     */
    @PostMapping("/register")
    public String register(
        @Valid UserRegisterVo userRegister,
        BindingResult result,
        RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("data", userRegister);
        String validResult = validRegisterData(userRegister, result, redirectAttributes);
        if (validResult != null) {
            return validResult;
        }

        // 使用远程服务进行注册
        R registerResult = memberServerFeign.register(userRegister);
        if (!registerResult.getCode().equals(0)) {
            HashMap<String, String> errors = new HashMap<>(1);
            if (registerResult.getCode().equals(BizCodeEnum.USERNAME_EXIST_EXCEPTION.getCode())) {
                errors.put("userName", registerResult.getMsg());
            } else if (registerResult.getCode().equals(BizCodeEnum.MOBILE_EXIST_EXCEPTION.getCode())) {
                errors.put("phone", registerResult.getMsg());
            } else {
                errors.put("msg", registerResult.getMsg());
            }
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/reg.html";
        }
        return "redirect:http://auth.gulimall.com/login.html";
    }

    /**
     * 校验注册信息
     * @param userRegister 注册信息
     * @param result jsr303 valid 封装错误结果集
     * @param redirectAttributes spring 的自定义域，可用于重定向时获取数据
     * @return String
     * @author Clover You
     * @date 2022/2/11 3:23 上午
     */
    private String validRegisterData(
        UserRegisterVo userRegister,
        BindingResult result,
        RedirectAttributes redirectAttributes
    ) {
        //region 处理错误消息
        if (result.hasErrors()) {
            // 处理错误消息传回页面
            Map<String, String> errors = result.getFieldErrors().stream().collect(
                Collectors.toMap(
                    FieldError::getField,
                    (target) -> Optional.ofNullable(target.getDefaultMessage()).orElse(""),
                    (o1, o2) -> o1
                )
            );
//            model.addAttribute("errors", errors);
            redirectAttributes.addFlashAttribute("errors", errors);
//            return "reg";
            return "redirect:http://auth.gulimall.com/reg.html";
        }
        // endregion

        //region 校验验证码
        String code = userRegister.getCode();
        String phone = userRegister.getPhone();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String codeInfo = ops.get(AuthServerConstant.REG_SMS_CODE_CACHE_PREFIX + phone);
        if (StringUtils.hasText(codeInfo)) {
            String[] codeInfos = codeInfo.split("_");
            if (!codeInfos[0].equalsIgnoreCase(code)) {
                Map<String, String> errors = new HashMap<>(1);
                errors.put("code", "验证码错误");
                redirectAttributes.addFlashAttribute("errors", errors);
                return "redirect:http://auth.gulimall.com/reg.html";
            }
            // 验证码校验成功，验证码作废删除
            redisTemplate.delete(AuthServerConstant.REG_SMS_CODE_CACHE_PREFIX + phone);
        } else {
            Map<String, String> errors = new HashMap<>(1);
            errors.put("code", "验证码错误");
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/reg.html";
        }
        // endregion
        return null;
    }


    /**
     * 用户登录
     * @param userLogin 登录信息
     * @return String
     * @author Clover You
     * @date 2022/2/11 7:02 下午
     */
    @PostMapping("/go")
    public String login(UserLoginVo userLogin) {
        return "redirect:http://www.gulimall.com";
    }
}
