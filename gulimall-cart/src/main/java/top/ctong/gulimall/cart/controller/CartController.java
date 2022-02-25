package top.ctong.gulimall.cart.controller;

import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.ctong.gulimall.cart.interceptor.CartInterceptor;
import top.ctong.gulimall.cart.service.CartService;
import top.ctong.gulimall.cart.to.UserInfoTo;
import top.ctong.gulimall.cart.vo.Cart;
import top.ctong.gulimall.cart.vo.CartItem;
import top.ctong.gulimall.common.constant.SessionKeyConstant;
import top.ctong.gulimall.common.utils.R;

import javax.servlet.http.HttpSession;
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
    public String cartListPage(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartList";
    }

    /**
     * 添加购物车
     * @param skuId 商品规格ID
     * @param num 添加数量
     * @param redAttr Spring重定向支持
     * @return String
     * @author Clover You
     * @date 2022/2/18 9:40 下午
     */
    @GetMapping("/addToCart")
    public String addToCart(
        @RequestParam("skuId") Long skuId,
        @RequestParam("num") Integer num,
        RedirectAttributes redAttr) {
        CartItem cartItem = cartService.addToCart(skuId, num);
        redAttr.addAttribute("skuId", cartItem.getSkuId());
        return "redirect:http://cart.gulimall.com/addToCartSuccess.html";
    }

    /**
     * 添加成功页
     * @return String
     * @author Clover You
     * @date 2022/2/18 9:41 下午
     */
    @GetMapping("/addToCartSuccess.html")
    public String successPage(@RequestParam("skuId") Long skuId, Model model) {
        CartItem cartItem = cartService.getCartItem(skuId);
        model.addAttribute("item", cartItem);
        return "success";
    }

    /**
     * 更新购物项选中状态
     * @param check 选中状态
     * @param skuId 购物项id
     * @return String
     * @author Clover You
     * @date 2022/2/19 5:19 下午
     */
    @GetMapping("/checkItem")
    public String itemChecked(@RequestParam(required = true, value = "check") Integer check,
                              @RequestParam(required = true, value = "skuId") Long skuId) {
        cartService.checkedItem(skuId, check == 1);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    /**
     * 修改购物项数量
     * @param skuId 购物项id
     * @param num 数量
     * @return String
     * @author Clover You
     * @date 2022/2/19 5:38 下午
     */
    @GetMapping("/countItem")
    public String countItem(@RequestParam(required = true, value = "skuId") Long skuId,
                            @RequestParam(required = true, value = "num") Integer num) {
        cartService.countItem(skuId, num);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    /**
     * 删除购物项
     * @param skuId 购物项id
     * @return String
     * @author Clover You
     * @date 2022/2/19 7:19 下午
     */
    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam(required = true, value = "skuId") Long skuId) {
        cartService.deleteItemBySkuId(skuId);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    /**
     * 获取但前登录用户的所有购物项
     * @return R
     * @author Clover You
     * @date 2022/2/25 2:42 下午
     */
    @GetMapping("CurrentUserCartItem")
    public R getCurrentUserCartItem() {
        List<CartItem> list = cartService.getUserCartItems();
        return R.ok().setData(list);
    }
}
