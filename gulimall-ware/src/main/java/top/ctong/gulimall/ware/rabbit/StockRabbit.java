package top.ctong.gulimall.ware.rabbit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.ctong.gulimall.common.to.mq.OrderTo;
import top.ctong.gulimall.common.to.mq.StockDetailTo;
import top.ctong.gulimall.common.to.mq.StockLockedTo;
import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.ware.components.RabbitComponent;
import top.ctong.gulimall.ware.entity.WareOrderTaskDetailEntity;
import top.ctong.gulimall.ware.entity.WareOrderTaskEntity;
import top.ctong.gulimall.ware.feign.OrderFeignService;
import top.ctong.gulimall.ware.service.WareOrderTaskDetailService;
import top.ctong.gulimall.ware.service.WareOrderTaskService;
import top.ctong.gulimall.ware.service.WareSkuService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
 * 库存管理
 * </p>
 * @author Clover You
 * @email cloveryou02@163.com
 * @create 2022-03-07 8:45 上午
 */
@Slf4j
@Service
@RabbitListener(queues = RabbitComponent.STOCK_RELEASE_STOCK_QUEUE_NAME)
public class StockRabbit {

    private WareOrderTaskDetailService wareOrderTaskDetailService;

    private WareOrderTaskService wareOrderTaskService;

    private OrderFeignService orderFeignService;

    private WareSkuService wareSkuService;

    @Autowired
    public void setWareSkuService(WareSkuService wareSkuService) {
        this.wareSkuService = wareSkuService;
    }

    @Autowired
    public void setOrderFeignService(OrderFeignService orderFeignService) {
        this.orderFeignService = orderFeignService;
    }

    @Autowired
    public void setWareOrderTaskService(WareOrderTaskService wareOrderTaskService) {
        this.wareOrderTaskService = wareOrderTaskService;
    }

    @Autowired
    public void setWareOrderTaskDetailService(WareOrderTaskDetailService wareOrderTaskDetailService) {
        this.wareOrderTaskDetailService = wareOrderTaskDetailService;
    }

    /**
     * 解锁库存
     * @param channel 信道
     * @param message 消息信息
     * @param to 数据
     * @author Clover You
     * @date 2022/3/7 3:53 下午
     */
    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    public void handleStockLockRelease(Channel channel, Message message, StockLockedTo to) throws IOException {
        log.info("to data: ====>> {}", to);
        try {
            StockDetailTo detail = to.getDetail();
            Long detailId = detail.getId();
            // 通过详情id查询库存锁定详情信息
            WareOrderTaskDetailEntity detailMeta = wareOrderTaskDetailService.getById(detailId);
            if (detailMeta == null) {
                // 如果查不到一个工作单，那么就可能已经自动解锁了(出现问题自动回滚了)
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }
            //#region 检查当前工作单对应的订单是否存在，如果不存在则必须解锁
            Long taskId = detailMeta.getTaskId();
            // 查询库存工作单
            WareOrderTaskEntity taskEntity = wareOrderTaskService.getById(taskId);
            // 订单
            String orderSn = taskEntity.getOrderSn();

            // 如果存在这个订单，那么该订单是否异常，如果异常那么就解锁库存，例如支付超时
            // 查询订单信息
            R orderStatus = orderFeignService.getOrderStatus(orderSn);
            if (orderStatus.getCode() != 0) {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                return;
            }
            OrderTo data = orderStatus.getData(new TypeReference<OrderTo>() {
            });
            if (data == null || data.getStatus() == 4 || data.getStatus() == 0) {
                // 已被取消
                if (detailMeta.getLockStatus() == 1) {
                    unLockStock(detail);
                }
            } else {
                // 订单已经付款了
                // 查询还没解锁的库存信息
                List<WareOrderTaskDetailEntity> list = wareOrderTaskDetailService.list(
                    new QueryWrapper<WareOrderTaskDetailEntity>()
                        .eq("task_id", taskEntity.getId())
                        .eq("lock_status", 1)
                );
                // 批量修改工作单库存锁定状态为已扣减
                wareOrderTaskDetailService.updateBatchById(
                    list.stream().map((item) -> {
                        WareOrderTaskDetailEntity detailEntity = new WareOrderTaskDetailEntity();
                        detailEntity.setId(item.getId());
                        detailEntity.setLockStatus(3);
                        return detailEntity;
                    }).collect(Collectors.toList())
                );
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            //#endregion
        } catch (Exception e) {
            // 处理时出现异常，消息重新归队
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }


    }

    /**
     * 解锁库存
     * @param detailTo 库存工作详情单
     * @author Clover You
     * @date 2022/3/7 7:26 下午
     */
    private void unLockStock(StockDetailTo detailTo) {
        wareSkuService.unLockStock(detailTo.getSkuId(), detailTo.getWareId(), detailTo.getSkuNum());
        // 修改库存工作单状态为已解锁
        WareOrderTaskDetailEntity entity = new WareOrderTaskDetailEntity();
        entity.setId(detailTo.getId());
        entity.setLockStatus(2);
        wareOrderTaskDetailService.updateById(entity);
    }

    /**
     * 通过订单号获取订单信息
     * @param orderSn 订单号
     * @return OrderTo
     * @author Clover You
     * @date 2022/3/7 7:20 下午
     */
    private OrderTo getOrderInfo(String orderSn) {
        R orderStatus = orderFeignService.getOrderStatus(orderSn);
        return orderStatus.getData(new TypeReference<OrderTo>() {
        });
    }

    /**
     * <h4>补偿服务</h4>
     * <p>
     * 订单服务主动推送解锁库存，防止订单服务网络抖动，
     * 导致库存解锁服务检查时发现订单还没取消，导致永远无法解锁库存
     * @param channel 信道
     * @param message 消息信息
     * @param to 订单消息
     * @author Clover You
     * @date 2022/3/8 4:17 下午
     */
    @RabbitHandler
    @Transactional(rollbackFor = Exception.class)
    public void handleStockLockRelease(Channel channel, Message message, OrderTo to)
        throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();


        String orderSn = to.getOrderSn();


        WareOrderTaskEntity orderTaskEntity = wareOrderTaskService.getOrderTaskByOrderSn(orderSn);
        if (orderTaskEntity == null) {
            channel.basicReject(deliveryTag, false);
            return;
        }
        // 查询还没解锁的库存信息
        List<WareOrderTaskDetailEntity> list = wareOrderTaskDetailService.list(
            new QueryWrapper<WareOrderTaskDetailEntity>()
                .eq("task_id", orderTaskEntity.getId())
                .eq("lock_status", 1)
        );

        //#region 订单是否已付款
        R orderInfoR = orderFeignService.getOrderStatus(orderSn);
        if (orderInfoR.getCode() != 0) {
            channel.basicReject(deliveryTag, true);
        }
        OrderTo data = orderInfoR.getData(new TypeReference<OrderTo>() {
        });
        if (data.getStatus() != 0 && data.getStatus() != 4 && data.getStatus() != 5) {
            List<WareOrderTaskDetailEntity> detailEntityList = list.stream().map((item) -> {
                WareOrderTaskDetailEntity detailEntity = new WareOrderTaskDetailEntity();
                detailEntity.setId(item.getId());
                detailEntity.setLockStatus(3);
                return detailEntity;
            }).collect(Collectors.toList());
            wareOrderTaskDetailService.updateBatchById(detailEntityList);
            channel.basicAck(deliveryTag, false);
            return;
        }
        //#endregion

        // 没付款且超时
        for (WareOrderTaskDetailEntity detailEntity : list) {
            StockDetailTo detail = new StockDetailTo();
            BeanUtils.copyProperties(detailEntity, detail);
            unLockStock(detail);
        }
        channel.basicAck(deliveryTag, false);
    }

}
