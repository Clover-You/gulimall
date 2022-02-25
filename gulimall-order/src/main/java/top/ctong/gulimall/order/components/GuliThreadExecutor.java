package top.ctong.gulimall.order.components;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
 * 线程配置
 * </p>
 *
 * @author Clover You
 * @create 2022-02-06 9:59 下午
 */
@Component
@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
public class GuliThreadExecutor {

    /**
     * 自定义线程池
     * @return ThreadPoolExecutor
     * @author Clover You
     * @date 2022/2/6 10:10 下午
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties poolConfig) {
        return new ThreadPoolExecutor(
                poolConfig.getCorePoolSize(),
                poolConfig.getMaximumPoolSize(),
                poolConfig.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(poolConfig.getQueueSize()),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
