package top.ctong.gulimall.auth.controller;

import com.alibaba.nacos.common.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.ctong.gulimall.common.feign.ThirdPartyFeignService;
import top.ctong.gulimall.common.utils.R;

import java.util.UUID;

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
        String substring = UUID.randomUUID().toString().substring(0, 6);
        thirdPartyFeignService.sendCode(phone, substring);
        return R.ok();
    }

}
