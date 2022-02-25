package top.ctong.gulimall.order.vo;

import lombok.Data;
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
    private BigDecimal totalPrice;

    /**
     * 最终价格（因付价格）
     */
    private BigDecimal payPrice;
}
