package top.ctong.gulimall.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
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
 * Copyright 2022 Clover You.
 * <p>
 * 消息队列组件
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-03-03 3:18 PM
 */
@Slf4j
@Configuration
public class AmqpMqConfig {

    /**
     * 延时队列
     * @return Queue
     * @author Clover You
     * @date 2022/3/3 3:20 PM
     */
    @Bean
    public Queue orderDelayQueue() {
        log.info("orderDelayQueue");
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", "order-event-exchange");
        args.put("x-dead-letter-routing-key", "order.release.order");
        args.put("x-message-ttl", 60000);
        return new Queue(
            "order.delay.queue",
            true,
            false,
            false,
            args
        );
    }

    /**
     * 死信队列
     * @return Queue
     * @author Clover You
     * @date 2022/3/3 3:26 PM
     */
    @Bean
    public Queue orderReleaseOrderQueue() {
        return new Queue(
            "order.release.order.queue",
            true,
            false,
            false
        );
    }

    /**
     * 死信路由
     * @return Exchange
     * @author Clover You
     * @date 2022/3/3 3:25 PM
     */
    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange("order-event-exchange", true, false);
    }

    /**
     * 延时队列绑定
     * @return Binding
     * @author Clover You
     * @date 2022/3/3 3:25 PM
     */
    @Bean
    public Binding orderCreateOrderBinding() {
        return new Binding(
            "order.delay.queue",
            Binding.DestinationType.QUEUE,
            "order-event-exchange",
            "order.create.order",
            null
        );
    }

    /**
     * 死信队列绑定
     * @return Binding
     * @author Clover You
     * @date 2022/3/3 3:25 PM
     */
    @Bean
    public Binding orderReleaseOrderBinding() {
        return new Binding(
            "order.release.order.queue",
            Binding.DestinationType.QUEUE,
            "order-event-exchange",
            "order.release.order",
            null
        );
    }

    /**
     * 库存释放绑定
     * @return Binding
     * @author Clover You
     * @date 2022/3/8 4:07 下午
     */
    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding(
            "stock.release.stock.queue",
            Binding.DestinationType.QUEUE,
            "order-event-exchange",
            "order.release.other.#",
            null
        );
    }
}
