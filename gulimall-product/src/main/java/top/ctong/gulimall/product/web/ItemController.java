package top.ctong.gulimall.product.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.ctong.gulimall.product.service.SkuInfoService;
import top.ctong.gulimall.product.vo.SkuItemVo;

import java.util.concurrent.ExecutionException;

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
 * 商品详情页
 * </p>
 *
 * @author Clover You
 * @create 2022-02-02 9:14 下午
 */
@Controller
@Slf4j
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 展示当前sku的详情
     * @author Clover You
     * @date 2022/2/2 9:27 下午
     * @param skuId 商品id
     * @return String
     */
    @RequestMapping("/{skuId}.html")
    public String skuItemPage(@PathVariable("skuId") Long skuId, Model model) throws ExecutionException, InterruptedException {
        log.info("sku id {}", skuId);
        SkuItemVo skuItemVo = skuInfoService.item(skuId);
        model.addAttribute("skuItem", skuItemVo);
        return "item";
    }
}
