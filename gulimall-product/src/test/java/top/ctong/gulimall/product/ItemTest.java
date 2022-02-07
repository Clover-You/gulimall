package top.ctong.gulimall.product;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ctong.gulimall.product.entity.SkuInfoEntity;
import top.ctong.gulimall.product.service.SkuInfoService;
import top.ctong.gulimall.product.vo.SkuItemVo;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

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
 * 商品详情测试
 * </p>
 *
 * @author Clover You
 * @create 2022-02-05 4:36 下午
 */
@SpringBootTest
@Slf4j
public class ItemTest {

    @Autowired
    private SkuInfoService skuInfoService;


    @Test
    @DisplayName("item test")
    void itemTest() throws ExecutionException, InterruptedException {
        long ctl = System.currentTimeMillis();
        SkuItemVo item = skuInfoService.item(73L);
        log.info("执行时间： {}" , System.currentTimeMillis() - ctl);
        assert item != null;
        log.info("skusBySpuId: {}", JSON.toJSONString(item));
    }

}
