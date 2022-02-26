package top.ctong.gulimall.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.common.vo.MemberRespVo;
import top.ctong.gulimall.order.components.GuliThreadExecutor;
import top.ctong.gulimall.order.components.interceptor.LoginInterceptor;
import top.ctong.gulimall.order.dao.OrderDao;
import top.ctong.gulimall.order.entity.OrderEntity;
import top.ctong.gulimall.order.feign.CartFeignService;
import top.ctong.gulimall.order.feign.MemberFeignService;
import top.ctong.gulimall.order.feign.ProductFeignService;
import top.ctong.gulimall.order.feign.WmsFeignService;
import top.ctong.gulimall.order.service.OrderService;
import top.ctong.gulimall.order.to.MemberAddressTo;
import top.ctong.gulimall.order.to.SkuHasStockTo;
import top.ctong.gulimall.order.vo.OrderConfirmVo;
import top.ctong.gulimall.order.vo.OrderItemVo;


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
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private MemberFeignService memberFeignService;

    @Autowired
    private CartFeignService cartFeignService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private WmsFeignService wmsFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
            new Query<OrderEntity>().getPage(params),
            new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 订单确认信息
     * @return OrderConfirmVo
     * @author Clover You
     * @date 2022/2/25 2:03 下午
     */
    @Override
    public OrderConfirmVo confirmOrder() {
        MemberRespVo mrv = LoginInterceptor.THREAD_LOCAL.get();
        OrderConfirmVo vo = new OrderConfirmVo();
        // 获取当前线程ThreadLocal
        RequestAttributes myReqContext = RequestContextHolder.currentRequestAttributes();

        // 查询会员所有收货地址
        CompletableFuture<Void> memberFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(myReqContext);
            R memberReceiveAddress = memberFeignService.getMemberReceiveAddress(mrv.getId());
            if (memberReceiveAddress.getCode() == 0) {
                List<MemberAddressTo> addresses = memberReceiveAddress.getData(new TypeReference<List<MemberAddressTo>>() {
                });
                vo.setAddress(addresses);
                return;
            }
            vo.setAddress(new ArrayList<>(0));

        }, executor);

        // 获取购物项
        CompletableFuture<Void> cartItemFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(myReqContext);
            R cartItem = cartFeignService.getCurrentUserCartItem();
            if (cartItem.getCode() != 0) {
                vo.setItems(new ArrayList<>(0));
                return;
            }
            List<OrderItemVo> itemData = cartItem.getData(new TypeReference<List<OrderItemVo>>() {
            });
            vo.setItems(itemData);
        }, executor);

        // 库存设置
        cartItemFuture.thenRunAsync(() -> {
            List<Long> skuIds = vo.getItems().stream().map(OrderItemVo::getSkuId).collect(Collectors.toList());
            R stock = wmsFeignService.getSkuHasStock(skuIds);
            if (stock.getCode() != 0) {
                return;
            }
            List<SkuHasStockTo> list = stock.getData(new TypeReference<List<SkuHasStockTo>>() {
            });
            Map<Long, Boolean> stocks = list.stream().collect(Collectors.toMap(
                SkuHasStockTo::getSkuId,
                SkuHasStockTo::getHasStock
            ));

            vo.setStocks(stocks);
        }, executor);

        // 积分设置
        vo.setIntegration(mrv.getIntegration());
        CompletableFuture.allOf(memberFuture, cartItemFuture).join();
        return vo;
    }

}