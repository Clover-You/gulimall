package top.ctong.gulimall.order.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.ctong.gulimall.order.service.OrderService;
import top.ctong.gulimall.order.vo.OrderConfirmVo;
import top.ctong.gulimall.order.vo.OrderSubmitVo;
import top.ctong.gulimall.order.vo.SubmitOrderResponseVo;

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
 * 页面映射前端控制器
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-24 8:17 下午
 */
@Controller
@Slf4j
public class PageController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单结算页
     * @return String
     * @author Clover You
     * @date 2022/2/25 10:22 AM
     */
    @GetMapping("/toTrade")
    public String toTradePage(Model model) {
        OrderConfirmVo data = orderService.confirmOrder();
        model.addAttribute("data", data);
        return "confirm";
    }

    /**
     * 订单提交
     * @param vo 订单数据
     * @return String
     * @author Clover You
     * @date 2022/2/27 8:23 上午
     */
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo vo, Model model, RedirectAttributes rAttr) throws Exception {
        SubmitOrderResponseVo data = orderService.submitOrder(vo);
        if (!data.getCode().equals(0)) {
            String errorMsg = "下单失败:";
            switch (data.getCode()) {
                case 1:
                    errorMsg += "订单已失效，请重新提交";
                    break;
                case 2:
                    errorMsg += "订单中有商品价格已经改变了哦";
                    break;
                case 3:
                    errorMsg += "下单失败，库存不足";
                    break;
                default:
                    errorMsg += "未知异常";
            }
            rAttr.addFlashAttribute("errorMsg", errorMsg);
            return "redirect:http://order.gulimall.com/toTrade";
        }
        model.addAttribute("data", data.getOrder());
        return "pay";
    }
}
