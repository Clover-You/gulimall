package top.ctong.gulimall.thirdparty.component;

import lombok.Data;
import org.apache.http.HttpResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import top.ctong.gulimall.common.utils.HttpUtils;

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
 * 短信组件
 * </p>
 *
 * @author Clover You
 * @create 2022-02-07 7:22 下午
 */
@Data
@Component
@ConfigurationProperties("alibaba.cloud.sms")
public class SmsComponent {

    /**
     * api地址
     */
    private String host;

    private String path;

    /**
     * 密钥
     */
    private String appCode;

    /**
     * 签名id
     */
    private String smsSignId;

    /**
     * 短信模板id
     */
    private String templateId;

    /**
     * 发送短信验证码
     *
     * @param mobile 手机号
     * @param code   验证码
     * @param alive  验证码有效期
     * @author Clover You
     * @date 2022/2/7 7:25 下午
     */
    public void sendSmsCode(String mobile, String code, Integer alive) {
        String appcode = "389f6d8e7a5e4c8392743117e4115e43";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        querys.put("mobile", mobile);
        querys.put("param", "**code**:" + code + ",**minute**:" + alive);
        querys.put("smsSignId", this.smsSignId);
        querys.put("templateId", this.templateId);
        Map<String, String> bodys = new HashMap<>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(this.host, this.path, "POST", headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
