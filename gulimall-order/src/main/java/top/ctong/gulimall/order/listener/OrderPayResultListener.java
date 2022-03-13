package top.ctong.gulimall.order.listener;

import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ctong.gulimall.order.components.AlipayTemplate;
import top.ctong.gulimall.order.service.OrderService;
import top.ctong.gulimall.order.vo.PayAsyncVo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
 * 订单支付结果监听器
 * </p>
 * @author Clover You
 * @email cloveryou02@163.com
 * @create 2022-03-11 3:52 下午
 */
@Slf4j
@RestController
public class OrderPayResultListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AlipayTemplate alipayTemplate;

    /**
     * 接受阿里支付结果通知
     * @return String 响应success告诉阿里表示成功
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/11 3:54 下午
     */
    @PostMapping("/alipay/success")
    public String aliPayResult(PayAsyncVo vo, HttpServletRequest req) {
        try {
            //获取支付宝POST过来反馈信息
            Map<String,String[]> requestParams = req.getParameterMap();
            Map<String,String> params = new HashMap<String,String>(requestParams.size());
            for (String name : requestParams.keySet()) {
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
//                valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                params.put(name, valueStr);
            }

            log.info("params ===>> {}", params);
            boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                alipayTemplate.getAlipayPublicKey(),
                alipayTemplate.getCharset(),
                alipayTemplate.getSignType()
            ); //调用SDK验证签名
            log.info("signVerified ====>> {}", signVerified);
            if (!signVerified){
                return "签名验证失败";
            }
            orderService.handleAlipayResult(vo);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }
}
