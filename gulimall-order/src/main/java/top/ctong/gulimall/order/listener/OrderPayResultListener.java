package top.ctong.gulimall.order.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ctong.gulimall.order.vo.PayAsyncVo;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 接受阿里支付结果通知
     * @return String 响应success告诉阿里表示成功
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/11 3:54 下午
     */
    @PostMapping("/alipay/success")
    public String aliPayResult(PayAsyncVo params) {
        log.info("params: ====>> {}", JSON.toJSONString(params));
        return "success";
    }

    /**
     * {
     *     "gmt_create": [
     *         "2022-03-11 16:38:52"
     *     ],
     *     "charset": [
     *         "utf-8"
     *     ],
     *     "gmt_payment": [
     *         "2022-03-11 16:39:02"
     *     ],
     *     "notify_time": [
     *         "2022-03-11 16:39:03"
     *     ],
     *     "subject": [
     *         "华为p50 新品手机 可可茶金 8G+256G..."
     *     ],
     *     "sign": [
     *         "UpJ7ob958EOCKLExRy0BRb4QVTJOnUgEwuMTTHWfVoQTZsm/ScsvNW4bxNHBNkdL6XgjSPhVK8rvQTXUDEficwlLmKW5Ga27EAx128MRcHn6E+VfvwViWT2Ea1TSpR52E9HCgSPZmdluT3G0x2OgJ5Wyppr8RYFbs+nnDj8/KOregjiwwUJyukgU/ewsNUq/yf1AdcVxBeS8tQ+Jiy+ZDSjPlc3OjNjIIo5c9YGtiUw8bOdnbat8TMbC1aPkCkl112DQJhJEWqyGQxwJhtCJTHf5YdHofXzit4gh3SSrvD+aRiiw66ltQ/JijdLjyms9AK3NJqUDh39iF6G8876sAQ=="
     *     ],
     *     "buyer_id": [
     *         "2088622958068871"
     *     ],
     *     "body": [
     *         "[华为p50 新品手机]"
     *     ],
     *     "invoice_amount": [
     *         "6161.00"
     *     ],
     *     "version": [
     *         "1.0"
     *     ],
     *     "notify_id": [
     *         "2022031100222163902068870518884926"
     *     ],
     *     "fund_bill_list": [
     *         "[{\"amount\":\"6161.00\",\"fundChannel\":\"ALIPAYACCOUNT\"}]"
     *     ],
     *     "notify_type": [
     *         "trade_status_sync"
     *     ],
     *     "out_trade_no": [
     *         "202203111638399571502202331082326017"
     *     ],
     *     "total_amount": [
     *         "6161.00"
     *     ],
     *     "trade_status": [
     *         "TRADE_SUCCESS"
     *     ],
     *     "trade_no": [
     *         "2022031122001468870501831517"
     *     ],
     *     "auth_app_id": [
     *         "2021000119633732"
     *     ],
     *     "receipt_amount": [
     *         "6161.00"
     *     ],
     *     "point_amount": [
     *         "0.00"
     *     ],
     *     "app_id": [
     *         "2021000119633732"
     *     ],
     *     "buyer_pay_amount": [
     *         "6161.00"
     *     ],
     *     "sign_type": [
     *         "RSA2"
     *     ],
     *     "seller_id": [
     *         "2088621957985705"
     *     ]
     * }
     */
}
