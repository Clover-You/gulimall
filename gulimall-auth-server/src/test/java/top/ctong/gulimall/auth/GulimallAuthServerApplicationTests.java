package top.ctong.gulimall.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import top.ctong.gulimall.common.feign.ThirdPartyFeignService;


@SpringBootTest
class GulimallAuthServerApplicationTests {

	@Autowired
	private ThirdPartyFeignService thirdPartyFeignService;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("测试短信发送")
	void sendSmsTest() {
		thirdPartyFeignService.sendCode("18933797903", "347186");
	}
}
