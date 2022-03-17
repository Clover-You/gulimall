package top.ctong.gulimall.order.rabbit;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ctong.gulimall.common.to.mq.SeckillOrderTo;
import top.ctong.gulimall.order.service.OrderService;

import java.io.IOException;

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
 * 订单秒杀监听器
 * </p>
 * @author Clover You
 * @email cloveryou02@163.com
 * @create 2022-03-16 6:06 下午
 */
@Slf4j
@Service
@RabbitListener(queues = "order.seckill.order.queue")
public class OrderSeckillListener {

    @Autowired
    private OrderService orderService;

    /**
     * 处理秒杀服务的订单
     * @param channel 信道
     * @param message 消息
     * @param seckillOrderTo 订单数据
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/16 6:08 下午
     */
    @RabbitHandler
    public void handler(Channel channel, Message message, SeckillOrderTo seckillOrderTo) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("准备创建秒杀单");
        try {
            orderService.createSeckillOrder(seckillOrderTo);
        } catch (Exception e) {
            log.error("秒杀单处理错误：===>> {}", e.getMessage());
            channel.basicReject(deliveryTag, true);
        }
    }

}
