package top.ctong.gulimall.order.components;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
 * 线程池自动配置
 * </p>
 *
 * @author Clover You
 * @create 2022-02-06 10:11 下午
 */
@Data
@ConfigurationProperties("gulimall.thread")
public class ThreadPoolConfigProperties {

    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 自大线程数
     */
    private Integer maximumPoolSize;

    /**
     * 线程池阻塞队列大小
     */
    private Integer queueSize;

    /**
     * 扩容线程最大空闲时间，单位ms
     */
    private Integer keepAliveTime;

    public ThreadPoolConfigProperties() {
        int cpuCoreCount = Runtime.getRuntime().availableProcessors();
        // 核心线程数默认使用最大CPU核心数 + 2
        this.corePoolSize = cpuCoreCount + 2;
        // 扩容数默认使用CPU核心数*20
        this.maximumPoolSize = cpuCoreCount * 20;
        // 阻塞队列大小默认10w
        this.queueSize = 100000;
        // 最大空闲时间
        this.keepAliveTime = 10;
    }

}
