package top.ctong.gulimall.thirdparty;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ctong.gulimall.thirdparty.component.SmsComponent;

import java.util.Random;
import java.util.UUID;

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
 *
 * </p>
 *
 * @author Clover You
 * @create 2022-02-07 9:06 下午
 */
@Slf4j
@SpringBootTest
public class SmsComponentTest {
    @Autowired
    private SmsComponent smsComponent;

    @Test
    @DisplayName("验证码接口测试")
    void smsComponentTest() {
        smsComponent.sendSmsCode("18933797903", "1234", 30);
    }

    @Test
    @DisplayName("随机验证码")
    void randomCode() {
        String substring = UUID.randomUUID().toString().substring(0, 6);
        log.info("验证码：{}",substring);

        Random r = new Random();
        log.info("随机数字：{}",r.nextInt(9999));
    }
}
