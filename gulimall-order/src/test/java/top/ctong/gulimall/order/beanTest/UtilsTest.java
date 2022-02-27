package top.ctong.gulimall.order.beanTest;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
 * 工具类测试
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-27 10:06 上午
 */
@Slf4j
@SpringBootTest
public class UtilsTest {

    @Test
    @DisplayName("Test the IdWorker utils provide by mybatis-plus")
    void IdWorkerTest() {
        String uuid = IdWorker.get32UUID();
        String timeId = IdWorker.getTimeId();
        long id = IdWorker.getId();
        String idStr = IdWorker.getIdStr();
        String millisecond = IdWorker.getMillisecond();
        log.info("uuid: ===> {}", uuid);
        log.info("timeId: ===> {}", timeId);
        log.info("id: ===> {}", id);
        log.info("idStr: ===> {}", idStr);
        log.info("millisecond: ===> {}", millisecond);
    }

}
