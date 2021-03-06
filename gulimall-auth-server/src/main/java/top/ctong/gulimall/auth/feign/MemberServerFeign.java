package top.ctong.gulimall.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.ctong.gulimall.auth.po.gitee.GiteeUserInfo;
import top.ctong.gulimall.auth.vo.UserLoginVo;
import top.ctong.gulimall.auth.vo.UserRegisterVo;
import top.ctong.gulimall.common.utils.R;

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
 * 会员模块远程服务
 * </p>
 * @author Clover You
 * @create 2022-02-10 10:18 下午
 */
@FeignClient("gulimall-member")
public interface MemberServerFeign {

    /**
     * 调用远程会员注册服务
     * @param memberRegisterVo 会员信息
     * @return R
     * @author Clover You
     * @date 2022/2/10 10:23 下午
     */
    @PostMapping("/member/member/register")
    R register(@RequestBody UserRegisterVo memberRegisterVo);

    /**
     * 会员登录
     * @param memberLoginVo 登录凭证信息
     * @return R
     * @author Clover You
     * @date 2022/2/11 7:51 下午
     */
    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo memberLoginVo);

    @PostMapping("/member/member/oauth2/gitee/login")
    R giteeLogin(@RequestBody GiteeUserInfo userInfo);
}
