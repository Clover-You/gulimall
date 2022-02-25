package top.ctong.gulimall.order.vo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Service;
import top.ctong.gulimall.order.to.MemberAddressTo;

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
 * 订单确认页vo
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-25 11:07 AM
 */
@Data
public class OrderConfirmVo {

    /**
     * 收货地址列表
     */
    private List<MemberAddressTo> address;

    /**
     * 购物车所有商品（被选中）
     */
    private List<OrderItemVo> items;

    /**
     * 积分
     */
    private Integer integration;

    /**
     * 订单总额
     */
    @Setter(AccessLevel.NONE)
    private BigDecimal totalPrice;

    /**
     * 最终价格（因付价格）
     */
    @Setter(AccessLevel.NONE)
    private BigDecimal payPrice;

    /**
     * 防止重复提交令牌
     */
    private String orderToken;

    /**
     * 计算总价
     * @return BigDecimal
     * @author Clover You
     * @date 2022/2/25 3:21 下午
     */
    public BigDecimal getTotalPrice() {
        if (items == null || items.isEmpty()) {
            return new BigDecimal("0.00");
        }
        return items.stream().map(OrderItemVo::getPrice).reduce(
            new BigDecimal("0.00"),
            BigDecimal::add
        );
    }

    /**
     * 应付价格
     * @return BigDecimal
     * @author Clover You
     * @date 2022/2/25 3:22 下午
     */
    public BigDecimal getPayPrice() {
        return getTotalPrice();
    }
}
