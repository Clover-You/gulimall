package top.ctong.gulimall.coupon;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.ctong.gulimall.common.utils.Constant;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@SpringBootTest
class GulimallCouponApplicationTests {

    @Test
    void contextLoads() {
        LocalDate nowDate = LocalDate.now();
        log.info("now date: ===>> {}", nowDate);
        // 拿到未来三天的日期
        LocalDate tomorrowDate = nowDate.plusDays(1);
        log.info("tomorrow date: ===>> {}", tomorrowDate);
        LocalDate threeDays = nowDate.plusDays(2);
        log.info("three date: ===>> {}", threeDays);

        LocalTime minTime = LocalTime.MIN;
        LocalTime maxTime = LocalTime.MAX;


        String tdc = LocalDateTime.of(tomorrowDate, minTime)
            .format(DateTimeFormatter.ofPattern(Constant.DateUtils.YYYY_DD_MM_HH_MM_SS));

        String threeDc = LocalDateTime.of(threeDays, maxTime)
            .format(DateTimeFormatter.ofPattern(Constant.DateUtils.YYYY_DD_MM_HH_MM_SS));

        log.info("tdc: ===>> {}", tdc);
        log.info("threeDc: ===>> {}", threeDc);
    }

}
