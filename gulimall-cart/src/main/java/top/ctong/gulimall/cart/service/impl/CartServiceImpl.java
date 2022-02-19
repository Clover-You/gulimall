package top.ctong.gulimall.cart.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.ctong.gulimall.cart.feign.ProductFeignServer;
import top.ctong.gulimall.cart.interceptor.CartInterceptor;
import top.ctong.gulimall.cart.service.CartService;
import top.ctong.gulimall.cart.to.SkuInfoTo;
import top.ctong.gulimall.cart.to.UserInfoTo;
import top.ctong.gulimall.cart.vo.Cart;
import top.ctong.gulimall.cart.vo.CartItem;
import top.ctong.gulimall.common.utils.R;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
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
 * 购物车服务实现
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-17 9:46 下午
 */
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    @Autowired
    private ProductFeignServer productFeignServer;

    private final String CART_PREFIX = "gulimall::cart::";

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 将指定商品添加到购物车
     * @param skuId 商品规格id
     * @param num 添加数量
     * @return CartItem
     * @author Clover You
     * @date 2022/2/18 6:12 下午
     */
    @Override
    public CartItem addToCart(Long skuId, Integer num) {
        BoundHashOperations<String, String, CartItem> ops = getCartOps();
        // 如果购物车中有这个数据，那么就直接对购物车中的数量加上当前添加的数量
        CartItem cartItem = (CartItem) ops.get(skuId.toString());
        if (cartItem != null) {
            cartItem.setCount(cartItem.getCount() + num);
            ops.put(cartItem.getSkuId().toString(), cartItem);
            return cartItem;
        }
        // 如果购物车没有数据，那么查询该商品保存到购物车
        cartItem = new CartItem();

        CartItem finalCartItem = cartItem;
        CompletableFuture<Void> skuInfoFuture = CompletableFuture.runAsync(() -> {
            R info = productFeignServer.getSkuInfoBySkuId(skuId);
            SkuInfoTo skuInfo = info.getData("skuInfo", new TypeReference<SkuInfoTo>() {
            });

            finalCartItem.setCheck(true);
            finalCartItem.setImage(skuInfo.getSkuDefaultImg());
            finalCartItem.setCount(num);
            finalCartItem.setPrice(skuInfo.getPrice());
            finalCartItem.setTitle(skuInfo.getSkuTitle());
            finalCartItem.setSkuId(skuId);
        }, threadPoolExecutor);

        CompletableFuture<Void> skuAttrFuture = CompletableFuture.runAsync(() -> {
            List<String> skuSaleAttrValues = productFeignServer.getSkuSaleAttrValues(skuId);
            finalCartItem.setSkuAttr(skuSaleAttrValues);
        }, threadPoolExecutor);

        CompletableFuture.allOf(skuInfoFuture, skuAttrFuture).join();

        ops.put(cartItem.getSkuId().toString(), cartItem);
        ops.expire(30, TimeUnit.DAYS);

        return cartItem;
    }

    /**
     * 获取指定购物车redis操作流
     * @return BoundHashOperations<Object>
     * @author Clover You
     * @date 2022/2/18 6:24 下午
     */
    private BoundHashOperations<String, String, CartItem> getCartOps() {
        UserInfoTo userInfoTo = CartInterceptor.THREAD_LOCAL.get();
        String key = "";
        if (userInfoTo.getUserId() != null) {
            key = CART_PREFIX + userInfoTo.getUserId();
        } else {
            key = CART_PREFIX + userInfoTo.getUserKey();
        }

        return redisTemplate.boundHashOps(key);
    }

    /**
     * 根据商品规格id获取购物车内容
     * @param skuId 商品规格id@return CartItem
     * @author Clover You
     * @date 2022/2/18 10:09 下午
     */
    @Override
    public CartItem getCartItem(Long skuId) {
        BoundHashOperations<String, String, CartItem> ops = getCartOps();
        CartItem cartItem = ops.get(skuId.toString());
        return cartItem;
    }

    /**
     * 获取购物车所有数据
     * @return Cart
     * @author Clover You
     * @date 2022/2/19 3:21 下午
     */
    @Override
    public Cart getCart() {
        Cart cart = new Cart();
        UserInfoTo userInfoTo = CartInterceptor.THREAD_LOCAL.get();
        String opsKey = CART_PREFIX + userInfoTo.getUserKey();
        String userCacheKey = CART_PREFIX + userInfoTo.getUserId();

        // 获取临时购物车
        List<CartItem> byCache = getCartItemsByCache(opsKey);

        if (userInfoTo.getUserId() != null) {
            // 线上购物车和临时购物车合并
            if (!byCache.isEmpty()) {
                for (CartItem cartItem : byCache) {
                    addToCart(cartItem.getSkuId(), cartItem.getCount());
                }
            }
            List<CartItem> myCart = getCartItemsByCache(userCacheKey);
            cart.setItems(myCart);
        } else {
            cart.setItems(byCache);
        }

        // 如果已登录、清除临时购物车
        if (userInfoTo.getUserId() != null) {
            clearCacheCartByKey(opsKey);
        }
        return cart;
    }

    /**
     * 获取缓存中的购物车数据
     * @param opsKey 购物车键
     * @author Clover You
     * @date 2022/2/19 3:41 下午
     */
    private List<CartItem> getCartItemsByCache(String opsKey) {
        BoundHashOperations<String, String, CartItem> ops = redisTemplate.boundHashOps(opsKey);
        List<CartItem> values = ops.values();
        if (values != null && !values.isEmpty()) {
            return values;
        }
        return Collections.emptyList();
    }

    /**
     * 通过缓存键清空购物车
     * @param cacheKey 购物车缓存键
     * @author Clover You
     * @date 2022/2/19 4:10 下午
     */
    @Override
    public void clearCacheCartByKey(String cacheKey) {
        redisTemplate.delete(cacheKey);
    }

    /**
     * 更改购物项选中状态
     * @param skuId 商品规格id
     * @param check 选中状态
     * @author Clover You
     * @date 2022/2/19 5:07 下午
     */
    @Override
    public void checkedItem(Long skuId, Boolean check) {
        CartItem cartItem = getCartItem(skuId);
        if (cartItem == null || cartItem.getCheck().equals(check)) {
            return;
        }
        cartItem.setCheck(check);
        // 更新该购物项
        updateCartItem(cartItem);

    }

    /**
     * 更新购物项
     * @param cartItem 购物项
     * @author Clover You
     * @date 2022/2/19 5:16 下午
     */
    private void updateCartItem(CartItem cartItem) {
        Long skuId = cartItem.getSkuId();
        BoundHashOperations<String, String, CartItem> cartOps = getCartOps();
        cartOps.put(skuId.toString(), cartItem);
    }
}
