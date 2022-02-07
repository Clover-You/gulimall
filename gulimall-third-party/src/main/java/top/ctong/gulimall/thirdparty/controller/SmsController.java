package top.ctong.gulimall.thirdparty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.thirdparty.component.SmsComponent;

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
 * 短信验证码前端控制器
 * </p>
 *
 * @author Clover You
 * @create 2022-02-07 9:11 下午
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsComponent smsComponent;

    /**
     * 发送验证码
     *
     * @param mobile 发送的手机号
     * @param code   验证码
     * @return R
     * @author Clover You
     * @date 2022/2/7 9:15 下午
     */
    @GetMapping("/send/{mobile}/{code}")
    public R sendCode(@PathVariable("mobile") String mobile, @PathVariable("code") String code) {
        smsComponent.sendSmsCode(mobile, code, 30);
        return R.ok();
    }

}
