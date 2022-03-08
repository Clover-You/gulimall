package top.ctong.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.order.entity.OrderEntity;
import top.ctong.gulimall.order.vo.OrderConfirmVo;
import top.ctong.gulimall.order.vo.OrderSubmitVo;
import top.ctong.gulimall.order.vo.SubmitOrderResponseVo;

import java.util.Map;


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
 * Copyright 2021 Clover You.
 * <p>
 * 订单
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-16 16:11:06
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询订单确认信息
     * @return OrderConfirmVo
     * @author Clover You
     * @date 2022/2/25 2:03 下午
     */
    OrderConfirmVo confirmOrder();

    /**
     * 创建订单（下单）
     * @param vo 订单信息
     * @return SubmitOrderResponseVo
     * @author Clover You
     * @date 2022/2/27 9:17 上午
     */
    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo) throws Exception;

    /**
     * 根据订单号获取订单状态
     * @param orderSn 订单
     * @return OrderEntity
     * @author Clover You
     * @date 2022/3/7 4:45 下午
     */
    OrderEntity getOrderStatus(String orderSn);

    /**
     * 关闭订单
     * @param orderEntity 订单信息
     * @author Clover You
     * @date 2022/3/8 9:37 上午
     */
    void closeOrder(OrderEntity orderEntity);
}

