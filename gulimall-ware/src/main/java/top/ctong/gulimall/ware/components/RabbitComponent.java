package top.ctong.gulimall.ware.components;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;

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
 * Rabbit 相关组件注册
 * </p>
 * @author Clover You
 * @email cloveryou02@163.com
 * @create 2022-03-07 2:42 下午
 */
@Component
public class RabbitComponent {

    /**
     * 库存锁定、死信路由
     */
    public static final String STOCK_EXCHANGE_NAME = "stock-event-exchange";

    /**
     * 死信队列绑定key
     */
    public static final String DEAD_LETTER_BINDING_KEY = "stock.release.#";

    /**
     * 死信队列名称
     */
    public static final String STOCK_RELEASE_STOCK_QUEUE_NAME = "stock.release.stock.queue";

    /**
     * 延时队列名称
     */
    public static final String STOCK_DELAY_QUEUE_NAME = "stock.delay.queue";

    /**
     * 延时队列绑定key
     */
    public static final String STOCK_DELAY_BINDING_KEY = "stock.locked";

    /**
     * 消息过期时间
     */
    public static final Long MESSAGE_TTL = 120000L;

    /**
     * 库存交换机
     * @return Exchange
     * @author Clover You
     * @date 2022/3/7 8:47 上午
     */
    @Bean
    public Exchange stockEventExchange() {
        return new TopicExchange(
            STOCK_EXCHANGE_NAME,
            true,
            false
        );
    }

    /**
     * 延时队列
     * @return Queue
     * @author Clover You
     * @date 2022/3/7 8:58 上午
     */
    @Bean
    public Queue stockDelayQueue() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", STOCK_EXCHANGE_NAME);
        args.put("x-dead-letter-router-key", DEAD_LETTER_BINDING_KEY);
        args.put("x-message-ttl", MESSAGE_TTL);

        return new Queue(
            STOCK_DELAY_QUEUE_NAME,
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
     * @date 2022/3/7 9:07 上午
     */
    @Bean
    public Queue stockReleaseStockQueue() {
        return new Queue(STOCK_RELEASE_STOCK_QUEUE_NAME, true, false, false);
    }

    /**
     * 死信队列绑定
     * @return Binding
     * @author Clover You
     * @date 2022/3/7 9:15 上午
     */
    @Bean
    public Binding stockReleaseBinding() {
        return new Binding(
            STOCK_RELEASE_STOCK_QUEUE_NAME,
            Binding.DestinationType.QUEUE,
            STOCK_EXCHANGE_NAME,
            DEAD_LETTER_BINDING_KEY,
            null
        );
    }

    /**
     * 延时队列绑定
     * @return Binding
     * @author Clover You
     * @date 2022/3/7 9:16 上午
     */
    @Bean
    public Binding stockDelayQueueBinding() {
        return new Binding(
            STOCK_DELAY_QUEUE_NAME,
            Binding.DestinationType.QUEUE,
            STOCK_EXCHANGE_NAME,
            STOCK_DELAY_BINDING_KEY,
            null
        );
    }

}
