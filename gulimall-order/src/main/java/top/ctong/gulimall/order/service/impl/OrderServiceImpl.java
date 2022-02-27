package top.ctong.gulimall.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.common.vo.MemberRespVo;
import top.ctong.gulimall.order.components.GuliThreadExecutor;
import top.ctong.gulimall.order.components.interceptor.LoginInterceptor;
import top.ctong.gulimall.order.constant.OrderConstant;
import top.ctong.gulimall.order.dao.OrderDao;
import top.ctong.gulimall.order.entity.OrderEntity;
import top.ctong.gulimall.order.entity.OrderItemEntity;
import top.ctong.gulimall.order.enume.OrderStatusEnum;
import top.ctong.gulimall.order.feign.CartFeignService;
import top.ctong.gulimall.order.feign.MemberFeignService;
import top.ctong.gulimall.order.feign.ProductFeignService;
import top.ctong.gulimall.order.feign.WmsFeignService;
import top.ctong.gulimall.order.service.OrderService;
import top.ctong.gulimall.order.to.CreateOrderTo;
import top.ctong.gulimall.order.to.MemberAddressTo;
import top.ctong.gulimall.order.to.SkuHasStockTo;
import top.ctong.gulimall.order.vo.*;

import javax.sound.sampled.Line;


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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 保存订单提交时的数据
     */
    private final ThreadLocal<OrderSubmitVo> ORDER_SUBMIT_VO_THREAD_LOCAL = new ThreadLocal<>();

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

        // 设置防重令牌
        String uuidToken = UUID.randomUUID().toString().replace("-", "");
        vo.setOrderToken(uuidToken);
        String tokenKey = OrderConstant.USER_ORDER_TOKEN_PREFIX + mrv.getId().toString();
        stringRedisTemplate.opsForValue().set(tokenKey, uuidToken, 30, TimeUnit.MINUTES);

        CompletableFuture.allOf(memberFuture, cartItemFuture).join();

        return vo;
    }

    /**
     * 创建订单（下单）
     * @param vo 订单信息
     * @return SubmitOrderResponseVo
     * @author Clover You
     * @date 2022/2/27 9:17 上午
     */
    @Override
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo vo) {
        SubmitOrderResponseVo resp = new SubmitOrderResponseVo();
        MemberRespVo mrv = LoginInterceptor.THREAD_LOCAL.get();

        //#region 创建订单、验证 token、验证价格、锁库存
        // 验证 token（验证和删除 token 必须保证原子性）
        String tokenKey = OrderConstant.USER_ORDER_TOKEN_PREFIX + mrv.getId().toString();
        String token = vo.getOrderToken();

        // 获取一个具有原子性的lua脚本
        String verificationLuaScript = getVerificationLuaScriptForOrderToken();
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>(verificationLuaScript, Boolean.class);
        Boolean verificationResult = stringRedisTemplate.<Boolean>execute(
            redisScript,
            Collections.singletonList(tokenKey),
            token
        );

        // check verification result for token...
        if (Boolean.FALSE.equals(verificationResult)) {
            log.debug("create order service ===>> token verification fail...");
            resp.setCode(1);
            return resp;
        }

        ORDER_SUBMIT_VO_THREAD_LOCAL.set(vo);
        CreateOrderTo order = createOrder();


        //#endregion
        return resp;
    }

    /**
     * 获取一个redis可以验证指定 token 带有原子性操作的lua脚本
     * @return String
     * @author Clover You
     * @date 2022/2/27 9:35 上午
     */
    private String getVerificationLuaScriptForOrderToken() {
        return "if redis.call('get', KEYS[1]) == ARGV[1] then redis.call('del', KEYS[1]) else return 0";
    }

    /**
     * 创建订单
     * @return CreateOrderTo
     * @author Clover You
     * @date 2022/2/27 10:02 上午
     */
    private CreateOrderTo createOrder() {
        CreateOrderTo order = new CreateOrderTo();
        //#region 创建订单
        String orderNo = IdWorker.getTimeId();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderSn(orderNo);
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());

        OrderSubmitVo osVo = ORDER_SUBMIT_VO_THREAD_LOCAL.get();

        // 设置地址
        Long addrId = osVo.getAddrId();
        R fareR = wmsFeignService.getFare(addrId);

        if (fareR.getCode() != 0) {
            // 错误
            log.error("fare query fail...");
        }
        FareVo fare = fareR.getData(
            "memberReceiveAddress",
            new TypeReference<FareVo>() {
            }
        );
        order.setFare(fare.getFare());
        MemberAddressTo address = fare.getAddress();
        // 运费
        orderEntity.setFreightAmount(fare.getFare());
        // 省
        orderEntity.setReceiverProvince(address.getProvince());
        // 市
        orderEntity.setReceiverCity(address.getCity());
        // 区
        orderEntity.setReceiverRegion(address.getRegion());
        // 详细地址
        orderEntity.setReceiverDetailAddress(address.getDetailAddress());
        // 收货人邮编
        orderEntity.setReceiverPostCode(address.getPostCode());
        // 收货人手机号
        orderEntity.setReceiverPhone(address.getPhone());
        // 收货人姓名
        orderEntity.setReceiverName(address.getName());
        //#endregion

        //#region 创建订单项
        // 获取当前用户购物车数据
        R userCartInfoR = cartFeignService.getCurrentUserCartItem();
        if (!userCartInfoR.getCode().equals(0)) {
            log.error("create order service ===>> user cart query fail...");
        }
        List<OrderItemVo> dataList = userCartInfoR.getData(new TypeReference<List<OrderItemVo>>() {
        });
        if (dataList == null || dataList.isEmpty()) {
            log.error("create order service ===>> cart is empty...");
        }
        List<OrderItemEntity> itemEntityList = dataList.stream().map(data -> {
            OrderItemEntity itemEntity = buildOrderItem(data);
            itemEntity.setOrderSn(orderNo);
            return itemEntity;
        }).collect(Collectors.toList());
        //#endregion
        return order;
    }

    /** 
     * 构建订单项
     * @param data 订单项信息
     * @return OrderItemEntity 
     * @author Clover You 
     * @date 2022/2/27 11:09 上午
     */
    private OrderItemEntity buildOrderItem(OrderItemVo data) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setSkuId(data.getSkuId());
        // TODO 构建订单项
        return entity;
    }
}