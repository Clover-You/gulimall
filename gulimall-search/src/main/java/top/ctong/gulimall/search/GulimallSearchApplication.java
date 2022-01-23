package top.ctong.gulimall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

import top.ctong.gulimall.common.feign.ProductFeignService;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import top.ctong.gulimall.common.config.MybatisPlusConfig;

//@EnableDiscoveryClient
@EnableFeignClients(clients = {ProductFeignService.class})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GulimallSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimallSearchApplication.class, args);
	}

}
