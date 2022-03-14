package top.ctong.gulimall.order.components;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import top.ctong.gulimall.order.vo.PayVo;

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
 * 阿里支付
 * </p>
 * @author Clover You
 * @email cloveryou02@163.com
 * @create 2022/3/10 9:01 上午
 */

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    /**
     * 在支付宝创建的应用的id
     */
    private String appId = "2021000119633732";

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    private String merchantPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCGg37d3EWbpGwqQ/ZUZFw/mM9rksJuhxcFi2JvCfLp777LHS9eYDB8//YqwTSA/6HgStDbDdziDlRXazsWr0f3S85rY/iwx5yWlK3fKGXuGR4eeFqElNdwybsryy+Bd+nHH6MunfDgwlG4xTRURz/UmPo+p5YO89LK5+xoUXm8Lqaujo9SAzizmdYyLd4RIlPjdu1rebAvY55EwDDZbX9ro05W0eIXM20iRu7YO91pDMqDWpSJ1CEe7h1Jr9WLN1pmZSz2tkT5K1BMlCOKMh+WFs+TLErExf2rnuLF6fxA4zgpQc0MixawC5QwOIauVSdok669fhMUTt+5zfeJ6Qe9AgMBAAECggEAeYvKkIkgsOIjBxIAPQOuw5y+GJM1c5BIAi+P04wm3rJPMdP+YIYEan8TnlCUs9fKGxUfokDljbKrDY8MhpP+MHxkWm+sYXkNipdwgUxMxpgVuizAhjrEbdsrcQgZtiB3swFof5Ah/8Y2+AHiJiF3kRBmV0Uxd/NkTwrAeQbePGaPWaXFDH1jUToVqipofoMjMIVDovv2tHXagerBJHjTJEVrwqrf9/WynSvvYo6rk273mc70UYIgM/DbaHRZpZ+MlGdsnLrXUzgQ9OCvfADbmIsixloe4Rr8HNDtocbncnw2hc1IuJHRB66TJU3FzyD0BbGIjBrtL9oO/aNXzyvC4QKBgQDDDEVbmu3ouSkAPgmolNpdvqLcld+015c5uOXTRJGu3xMZ8EIz90aYCITqOJyV5zZC3ze//GQ4/Epv81yvt3VBQ41nA2ytHtxJk8QAySG64rP6z1VE7ZTBGNXbuJT5bpiSuhZMbaDHLcolNFWpBUAeTYWLGlz1DuCi5RqV84oZyQKBgQCwjIPeseGAus4ucFSY1wIXVju3VMrsUaT7Df5uepabKQ9Q8dFNzlpN0tIIOgqkqWUVNsfYSjI9x/K9+qqm92VIqsBszDk0ZiZSGwTL1HNwGlef61fn5iILfL8rWCse7FWdDVEXqRW27+8+BdrxAYw7M48/O2ptvusKnwsAOVa4VQKBgQDCtvBSl+Mjrh0JWeVof1lTtql9Tr1qGsOkYzpEY7KQAbBe96QPnq7Bvdd6v5NKbuyTa6WLKpqe7PYetrNz09Dhm1+BVEDcvu8pILPOGPxsRj9ohizYx9j1wCZW1/kY3VjBObZjDBoyXTrtU0akmlI9LiTADkx4dCa7V73nrp3SSQKBgGGUmMJ6sPgUyCcUwT1lAWvipsjy3eH2UmjM2Ec6DIogDK4hqh0GJBWFo78WTZGrawppp9WdVN2e6UUmCjKU5O9gKVE5I5kZqK6S7ni2qUNZ2/qw+npbUY/l2EfdyJ+j4sIWfS7FrxHY1fQDCntQ7rA+FDvP3EhiUMkjFNJ/3rWtAoGANSsxe0my80iHQNEJO8vCcIqVRGEsD9Ix3Jw4qHnoEyZVN+MKRpKFHOmfi+C074Wf8imkxjyKzCDeRCpxpOJo/5MkoekZp+ihCH3AxTVQHKccHlEJyAGOynT1WlpPh1VuSIuhn8k06Gi94wPM9TTLTLsl2sapdE81jkfvaR8AvV0=";

    /**
     * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     */
    private String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmpKJWkdZGnUzMYF8MvmXHJrbO4z5VLUYecgNX9uVPFY0zYJqC8rjIiAFmPNY56S6mWhQZ8pT4myubiZXpbVkTz3qrw5EvobixD6x/Q9VcVuzT8nYPq45/nVtr2CQZQfESyovf8/ccYnBSFiw9C7tYkkmfrbZZ9N4T9mpCwlafyYVYhWkcGjxQULt5lh1tOQl74ospsnfHSB0CLaJ5IlCv6tDwJevVWpVNhJtqEvfpFfvAO6Xlg+FCCR5pqLBOO6M6QABDDSWgM/ORscsGkFGgQ4HUolnkMwS0t5O3HViFlNamj9keig+S7ONRxRoaPk+WdXSix1Gmr1FvySSI9/guQIDAQAB";

    /**
     * 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     * 请求返回页
     */
    private String notifyUrl = "http://uuynex.natappfree.cc/alipay/success";

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     * 同步通知，支付成功，一般跳转到成功页
     */
    private String returnUrl = "http://member.gulimall.com/orderList";

    /**
     * 签名方式
     */
    private String signType = "RSA2";

    /**
     * 字符编码格式
     */
    private String charset = "utf-8";

    /**
     * 支付宝网关； https://openapi.alipaydev.com/gateway.do
     */
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
            appId, merchantPrivateKey, "json",
            charset, alipayPublicKey, signType);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String outTradeNo = vo.getOutTradeNo();
        //付款金额，必填
        String totalAmount = vo.getTotalAmount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\","
            + "\"total_amount\":\"" + totalAmount + "\","
            + "\"subject\":\"" + subject + "\","
            + "\"body\":\"" + body + "\","
            + "\"timeout_express\":\"1m\","
            + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应：" + result);

        return result;

    }
}
