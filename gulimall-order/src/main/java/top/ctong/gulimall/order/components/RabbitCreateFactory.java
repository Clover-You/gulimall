package top.ctong.gulimall.order.components;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.ctong.gulimall.order.properties.RabbitCreateFactoryConfigurationProperties;

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
 * RabbitMQ 工厂
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-21 9:55 下午
 */
@EnableConfigurationProperties(RabbitCreateFactoryConfigurationProperties.class)
@Component
public class RabbitCreateFactory {

    @Bean(name = "directExchange")
    public Exchange directExchange(RabbitCreateFactoryConfigurationProperties properties) {
        String exchangePrefix = properties.getExchangePrefix();
        DirectExchange exchange = new DirectExchange(
            StringUtils.hasText(exchangePrefix) ? exchangePrefix + "DirectExchange" : "DirectExchange",
            true,
            false,
            new HashMap<>()
        );
        return exchange;
    }

}
