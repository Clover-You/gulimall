package top.ctong.gulimall.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.ctong.gulimall.cart.interceptor.CartInterceptor;
import top.ctong.gulimall.cart.service.CartService;
import top.ctong.gulimall.cart.to.UserInfoTo;
import top.ctong.gulimall.cart.vo.CartItem;
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

    @Autowired
    private CartService cartService;

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
        return "cartList";
    }

    @GetMapping("/addToCart")
    public String addToCart(
        @RequestParam("skuId") Long skuId,
        @RequestParam("num") Integer num,
        Model model) {
        CartItem cartItem = cartService.addToCart(skuId, num);
        model.addAttribute("item", cartItem);
        return "success";
    }
}
