package top.ctong.gulimall.product.web;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.ctong.gulimall.product.entity.CategoryEntity;
import top.ctong.gulimall.product.service.CategoryService;
import top.ctong.gulimall.product.vo.Catalog2Vo;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
 *
 * @author Clover You
 * @create 2021-12-26 10:34
 */
@Slf4j
@Controller
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 首页
     *
     * @return String
     * @author Clover You
     * @date 2021/12/26 10:38
     */
    @GetMapping({"/", "/index", "/index.html"})
    public String indexPage(Model model) {
        // 查询所有一级分类
        List<CategoryEntity> list = categoryService.getLeve1Category();
        if (log.isDebugEnabled()) {
            log.info("一级分类信息: {}", list);
        }
        model.addAttribute("categorys", list);
        return "index";
    }

    /**
     * 获取分类
     *
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
    public String hello() throws InterruptedException {
        RLock lock = redisson.getLock("my-lock");
        lock.lock();
        Thread.sleep(30000l);
        return "hello world";
    }


    @ResponseBody
    @GetMapping("/write")
    public String write() {
        String s = UUID.randomUUID().toString();
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = lock.writeLock();
        try {
            rLock.lock();
            Thread.sleep(5000);
            redisTemplate.opsForValue().set("test", s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
        return s;
    }

    @ResponseBody
    @GetMapping("/read")
    public String read() {
        String s = "";
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = lock.readLock();
        try {
            rLock.lock();
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            String test = ops.get("test");
            return test;
        } finally {
            rLock.unlock();
        }
    }

    @GetMapping("park")
    @ResponseBody
    public String park() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
        park.acquire();// 获取一个信号量，获取一个值
        return "ok";
    }

    @GetMapping("go")
    @ResponseBody
    public String go() {
        RSemaphore park = redisson.getSemaphore("park");
        park.release();
        return "ok";
    }
}
