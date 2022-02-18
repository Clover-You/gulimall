package top.ctong.gulimall.cart.controller;

import org.apache.shiro.session.mgt.SessionKey;
import org.bouncycastle.asn1.mozilla.PublicKeyAndChallenge;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.ctong.gulimall.cart.interceptor.CartInterceptor;
import top.ctong.gulimall.cart.to.UserInfoTo;
import top.ctong.gulimall.common.constant.SessionKeyConstant;

import javax.servlet.http.HttpSession;

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
 * 购物车前端控制器
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-17 10:16 下午
 */
@Controller
public class CartController {

    /**
     * 购物车列表 页
     * @return String
     * @author Clover You
     * @date 2022/2/17 10:18 下午
     */
    @GetMapping("/cart.html")
    public String cartListPage(HttpSession session) {
        Object userInfo = session.getAttribute(SessionKeyConstant.LOGIN_USER);
        UserInfoTo userInfoTo = CartInterceptor.THREAD_LOCAL.get();
        if (userInfo == null) {
            return "redirect:http://gulimall.com";
        }
        return "cartList";
    }

}
