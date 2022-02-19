package top.ctong.gulimall.cart.service;

import top.ctong.gulimall.cart.vo.Cart;
import top.ctong.gulimall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

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
 * 购物车服务
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-17 9:46 下午
 */
public interface CartService {

    /**
     * 将指定商品添加到购物车
     * @param skuId 商品规格id
     * @param num 添加数量
     * @return CartItem
     * @author Clover You
     * @date 2022/2/18 6:12 下午
     */
    CartItem addToCart(Long skuId, Integer num);

    /**
     * 根据商品规格id获取购物车内容
     * @param skuId 商品规格id@return CartItem
     * @author Clover You
     * @date 2022/2/18 10:09 下午
     */
    CartItem getCartItem(Long skuId);

    /**
     * 获取购物车所有数据
     * @return Cart
     * @author Clover You
     * @date 2022/2/19 3:21 下午
     */
    Cart getCart();

    /**
     * 通过缓存键清空购物车
     * @param cacheKey 购物车缓存键
     * @author Clover You
     * @date 2022/2/19 4:10 下午
     */
    void clearCacheCartByKey(String cacheKey);

    /**
     * 更改购物项选中状态
     * @param skuId 商品规格id
     * @param check 选中状态
     * @author Clover You
     * @date 2022/2/19 5:07 下午
     */
    void checkedItem(Long skuId, Boolean check);

    /**
     * 修改购物项数量
     * @param skuId 购物项id
     * @param num 数量
     * @author Clover You
     * @date 2022/2/19 5:38 下午
     */
    void countItem(Long skuId, Integer num);
}
