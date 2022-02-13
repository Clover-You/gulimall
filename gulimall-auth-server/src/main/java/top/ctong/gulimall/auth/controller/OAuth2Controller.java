package top.ctong.gulimall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.ctong.gulimall.auth.feign.MemberServerFeign;
import top.ctong.gulimall.auth.po.GiteeSocialUser;
import top.ctong.gulimall.auth.po.gitee.GiteeUserInfo;
import top.ctong.gulimall.auth.vo.MemberRespVo;
import top.ctong.gulimall.common.utils.HttpUtils;
import top.ctong.gulimall.common.utils.R;

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
 * 社交登录
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-12 9:51 下午
 */
@Controller
@Slf4j
@RequestMapping("/oauth2.0")
public class OAuth2Controller {

    @Autowired
    private MemberServerFeign memberServerFeign;

    /**
     * 通过code获取access_token
     * @param code code授权码
     * @return String
     * @author Clover You
     * @date 2022/2/12 9:55 下午
     */
    @RequestMapping("/gitee/success")
    public String giteeAuth(@RequestParam("code") String code, RedirectAttributes rediAttr) throws Exception {
        log.info("code: {}", code);
        String clientSecret = "2f6d176f36ab8c6aad5caa5ac352088af70f04883a380a03de0f137e208fa8a4";
        String clientId = "93fd702d282049b9c61c5fe63166a6707f5fa742abd9c08b36d9846650bcfc6b";
        String redirectUri = "http://auth.gulimall.com/oauth2.0/gitee/success";

        Map<String, String> reqQuery = new HashMap<>(4);
        reqQuery.put("grant_type", "authorization_code");
        reqQuery.put("code", code);
        reqQuery.put("client_id", clientId);
        reqQuery.put("redirect_uri", redirectUri);

        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("client_secret", clientSecret);


        HashMap<String, String> reqHeaders = new HashMap<>(0);
        HttpResponse result = HttpUtils.doPost(
            "https://gitee.com",
            "/oauth/token",
            "post",
            reqHeaders,
            reqQuery,
            reqBody
        );

        int successCode = 200;
        if (result.getStatusLine().getStatusCode() != successCode) {
            HashMap<String, String> errors = new HashMap<>(1);
            errors.put("msg", "登录失败");
            rediAttr.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/login.html";
        }
        HttpEntity entity = result.getEntity();
        String targetJson = EntityUtils.toString(entity);
        GiteeSocialUser socialUser = JSON.parseObject(targetJson, GiteeSocialUser.class);

        // 查询用户资料
        HashMap<String, String> getUserInfoQuery = new HashMap<>();
        getUserInfoQuery.put("access_token", socialUser.getAccessToken());

        HttpResponse userInfoReqRes = HttpUtils.doGet(
            "https://gitee.com",
            "/api/v5/user",
            "get",
            new HashMap<>(1),
            getUserInfoQuery
        );
        if (userInfoReqRes.getStatusLine().getStatusCode() != successCode) {
            HashMap<String, String> errors = new HashMap<>(1);
            errors.put("msg", "登录失败");
            rediAttr.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/login.html";
        }

        HttpEntity userInfoHttpEntity = userInfoReqRes.getEntity();
        String userInfoJson = EntityUtils.toString(userInfoHttpEntity);
        GiteeUserInfo giteeUserInfo = JSON.parseObject(userInfoJson, GiteeUserInfo.class);

        R r = memberServerFeign.giteeLogin(giteeUserInfo);
        if (!r.getCode().equals(0)) {
            HashMap<String, String> errors = new HashMap<>(1);
            errors.put("msg", r.getMsg());
            rediAttr.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/login.html";
        }
        MemberRespVo memberData = r.getData(new TypeReference<MemberRespVo>() {});

        log.info("登录成功 ====>> {}", memberData);

        return "redirect:http://www.gulimall.com";
    }

//    public String getGiteeUserInfo() {
//
//    }
}
