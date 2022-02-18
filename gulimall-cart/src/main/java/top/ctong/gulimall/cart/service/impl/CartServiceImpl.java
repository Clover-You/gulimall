package top.ctong.gulimall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.ctong.gulimall.cart.feign.ProductFeignServer;
import top.ctong.gulimall.cart.interceptor.CartInterceptor;
import top.ctong.gulimall.cart.service.CartService;
import top.ctong.gulimall.cart.to.SkuInfoTo;
import top.ctong.gulimall.cart.to.UserInfoTo;
import top.ctong.gulimall.cart.vo.CartItem;
import top.ctong.gulimall.common.utils.R;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

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
        BoundHashOperations<String, String, Object> ops = getCartOps();
        // 如果购物车中有这个数据，那么就直接对购物车中的数量加上当前添加的数量
        String cacheSkuInfo = (String) ops.get(skuId.toString());
        if (StringUtils.hasText(cacheSkuInfo)) {
            CartItem cartItem = JSON.parseObject(cacheSkuInfo, CartItem.class);
            cartItem.setCount(cartItem.getCount() + num);
            ops.put(cartItem.getSkuId().toString(), cartItem);
            return cartItem;
        }
        // 如果购物车没有数据，那么查询该商品保存到购物车
        CartItem cartItem = new CartItem();

        CompletableFuture<Void> skuInfoFuture = CompletableFuture.runAsync(() -> {
            R info = productFeignServer.getSkuInfoBySkuId(skuId);
            SkuInfoTo skuInfo = info.getData("skuInfo", new TypeReference<SkuInfoTo>() {
            });

            cartItem.setCheck(true);
            cartItem.setImage(skuInfo.getSkuDefaultImg());
            cartItem.setCount(num);
            cartItem.setPrice(skuInfo.getPrice());
            cartItem.setTitle(skuInfo.getSkuTitle());
            cartItem.setSkuId(skuId);
        }, threadPoolExecutor);

        CompletableFuture<Void> skuAttrFuture = CompletableFuture.runAsync(() -> {
            List<String> skuSaleAttrValues = productFeignServer.getSkuSaleAttrValues(skuId);
            cartItem.setSkuAttr(skuSaleAttrValues);
        }, threadPoolExecutor);

        CompletableFuture.allOf(skuInfoFuture, skuAttrFuture).join();

        ops.put(cartItem.getSkuId().toString(), cartItem);

        return cartItem;
    }

    /**
     * 获取指定购物车redis操作流
     * @return BoundHashOperations<Object>
     * @author Clover You
     * @date 2022/2/18 6:24 下午
     */
    private BoundHashOperations<String, String, Object> getCartOps() {
        UserInfoTo userInfoTo = CartInterceptor.THREAD_LOCAL.get();
        String key = "";
        if (userInfoTo.getUserId() != null) {
            key = CART_PREFIX + userInfoTo.getUserId();
        } else {
            key = CART_PREFIX + userInfoTo.getUserKey();
        }

        return redisTemplate.boundHashOps(key);
    }
}
