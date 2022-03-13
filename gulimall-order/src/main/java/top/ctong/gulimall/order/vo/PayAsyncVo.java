package top.ctong.gulimall.order.vo;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class PayAsyncVo {

    private String gmtCreate;
    private String charset;
    private String gmtPayment;
    private String notifyTime;
    private String subject;
    private String sign;
    private String buyerId;//支付者的id
    private String body;//订单的信息
    private String invoiceAmount;//支付金额
    private String version;
    private String notifyId;//通知id
    private String fundBillList;
    private String notifyType;//通知类型； trade_status_sync
    private String outTradeNo;//订单号
    private String totalAmount;//支付的总额
    private String tradeStatus;//交易状态  TRADE_SUCCESS
    private String tradeNo;//流水号
    private String authAppId;//
    private String receiptAmount;//商家收到的款
    private String pointAmount;//
    private String appId;//应用id
    private String buyerPayAmount;//最终支付的金额
    private String signType;//签名类型
    private String sellerId;//商家的id

}
