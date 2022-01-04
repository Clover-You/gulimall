package top.ctong.gulimall.product.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ctong.gulimall.product.entity.CategoryEntity;
import top.ctong.gulimall.product.service.CategoryService;
import top.ctong.gulimall.product.vo.Catalog2Vo;

import java.util.List;
import java.util.Map;

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
 * 首页
 * </p>
 * @author Clover You
 * @create 2021-12-26 10:34
 */
@Slf4j
@Controller
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 首页
     * @return String
     * @author Clover You
     * @date 2021/12/26 10:38
     */
    @GetMapping({"/", "/index", "/index.html"})
    public String indexPage(Model model) {
        // 查询所有一级分类
        List<CategoryEntity> list = categoryService.getLeve1Category();
        if (log.isDebugEnabled()) {
            log.info("一级分类信息: {}",list);
        }
        model.addAttribute("categorys",list);
        return "index";
    }

    /** 
     * 获取分类
     * @return void
     * @author Clover You
     * @date 2021/12/26 14:46
     */
    @ResponseBody
    @GetMapping("index/catalog.json")
    public Map<String, List<Catalog2Vo>> getCatalogJson() throws InterruptedException {
        return categoryService.getCatalogJson();
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
