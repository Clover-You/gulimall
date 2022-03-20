package top.ctong.gulimall.member.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.ctong.gulimall.common.utils.Constant;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.member.feign.OrderFeignService;
import top.ctong.gulimall.member.vo.OrderVo;

import java.util.HashMap;
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
 * Copyright 2022 Clover You.
 * <p>
 * 前端控制器
 * </p>
 * @author Clover You
 * @email cloveryou02@163.com
 * @create 2022-03-10 3:55 下午
 */
@Slf4j
@Controller
public class MemberWebController {

    @Autowired
    private OrderFeignService orderFeignService;

    /**
     * 订单列表页
     * @return String
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/10 3:58 下午
     */
    @GetMapping("/orderList")
    public String orderList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {
        Map<String, Object> map = new HashMap<>(1);
        map.put(Constant.PAGE, pageNum.toString());
        R r = orderFeignService.listWithItem(map);
        PageUtils pageUtils = r.getData(
            "page",
            new TypeReference<PageUtils>() {
            }
        );

        List<OrderVo> o = JSONObject.parseObject(
            JSON.toJSONString(pageUtils.getList()), new TypeReference<List<OrderVo>>() {
            }.getType()
        );

        pageUtils.setList(o);
        log.info("data: ====>>> {}", JSON.toJSONString(pageUtils));

        model.addAttribute("list", pageUtils);
        return "orderList";
    }

}
