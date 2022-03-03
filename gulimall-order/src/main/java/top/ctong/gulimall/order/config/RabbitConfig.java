package top.ctong.gulimall.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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
 * spring rabbit 配置
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-24 9:01 上午
 */
@Slf4j
@Configuration
public class RabbitConfig {

    /**
     * 添加 Rabbit 消息转换器 [Jackson2JsonMessageConverter]
     * @return MessageConverter new Jackson2JsonMessageConverter
     * @author Clover You
     * @date 2022/2/24 9:30 上午
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * Confirmation callback.
             * @param correlationData 当前消息的唯一关联数据
             * @param ack 消息成功还是失败
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("==================== Confirm Callback ====================");
                log.info("initRabbitTemplate");
                log.info("ack: ===> {}", ack);
                log.info("cause: ===> {}", cause);
            }
        });

        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {

            /**
             * 如果消息没有抵达指定的 queue，那么就会触发这个失败回调
             * @param returned 消息和元数据
             */
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                log.info("==================== Returned Callback ====================");
                Message message = returned.getMessage();// 投递失败的消息
                String replyText = returned.getReplyText();// RabbitMQ回复信息
                String routingKey = returned.getRoutingKey();// 投递时使用的路由键
                int replyCode = returned.getReplyCode();// RabbitMQ 回复的状态码
                String exchange = returned.getExchange();// 当时消息是发给哪个交换机的
                log.info("message: {}", message);
                log.info("replyText: {}", replyText);
                log.info("routingKey: {}", routingKey);
                log.info("replyCode: {}", replyCode);
                log.info("exchange: {}", exchange);

            }
        });
    }
}
