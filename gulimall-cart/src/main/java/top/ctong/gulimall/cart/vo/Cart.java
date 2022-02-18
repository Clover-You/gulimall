package top.ctong.gulimall.cart.vo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;
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
 * 购物车
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-17 9:05 下午
 */
@Data
public class Cart {

    /**
     * 商品列表
     */
    private List<CartItem> items;

    /**
     * 商品件数总计
     */
    @Setter(AccessLevel.NONE)
    private Integer countNum;

    /**
     * 商品类型数量统计
     */
    @Setter(AccessLevel.NONE)
    private Integer countType;

    /**
     * 商品总价
     */
    @Setter(AccessLevel.NONE)
    private BigDecimal totalAmount;

    /**
     * 优惠价格
     */
    private BigDecimal reduce;

    /**
     * 获取购物车商品总数
     * @return Integer
     * @author Clover You
     * @date 2022/2/17 9:29 下午
     */
    public Integer getCountNum() {
        if (items == null || items.isEmpty()) {
            return 0;
        }
        return items.stream().filter(CartItem::getCheck)
            .map(CartItem::getCount).reduce(0, Integer::sum);
    }

    /**
     * 获取商品总类数量
     * @return Integer
     * @author Clover You
     * @date 2022/2/17 9:29 下午
     */
    public Integer getCountType() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    /**
     * 全部商品总价（已选check=true）
     * @return BigDecimal
     * @author Clover You
     * @date 2022/2/17 9:37 下午
     */
    public BigDecimal getTotalAmount() {
        if (items == null || items.isEmpty()) {
            return new BigDecimal("0.00");
        }

        BigDecimal totalPrice = items.stream().filter(CartItem::getCheck)
            .map(CartItem::getTotalPrice).reduce(new BigDecimal("0.00"), BigDecimal::add);

        final int zero = 0;
        if (totalPrice.compareTo(new BigDecimal(zero)) < 0) {
            return new BigDecimal("0.00");
        }

        return totalPrice.subtract(getReduce());
    }
}
