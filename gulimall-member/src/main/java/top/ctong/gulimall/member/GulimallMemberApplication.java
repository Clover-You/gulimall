package top.ctong.gulimall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

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
 * 用户模块
 * </p>
 * @author Clover You
 * @create 2021/11/16 16:00
 */
@EnableFeignClients(basePackages = "top.ctong.gulimall.common.feign")
@EnableDiscoveryClient
@MapperScan("top.ctong.gulimall.member.dao")
@SpringBootApplication
public class GulimallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallMemberApplication.class, args);
    }

}

/**
 * 远程调用别的服务
 * 1：引入open-feign
 * 2：open-feign是声明式的远程调用，所以需要编写一个接口，告诉SpringCloud这个接口需要调用远程服务
 * 3: 使用@FeignClient告诉spring cloud这个接口是个远程客户端
 * 4: 声明接口的每一个方法都是调用远程服务的哪个请求，接口的方法需要与目标方法签名一致
 * 5: 若需要使用远程调用，需要使用{@EnableFeignClients}开启远程调用功能
 */