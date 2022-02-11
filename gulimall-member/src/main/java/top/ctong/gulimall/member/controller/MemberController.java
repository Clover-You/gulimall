package top.ctong.gulimall.member.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sun.jvm.hotspot.ui.tree.RevPtrsTreeNodeAdapter;
import top.ctong.gulimall.common.exception.BizCodeEnum;
import top.ctong.gulimall.common.feign.CouponFeignService;
import top.ctong.gulimall.member.entity.MemberEntity;
import top.ctong.gulimall.member.exception.MobileExistException;
import top.ctong.gulimall.member.exception.UsernameExistException;
import top.ctong.gulimall.member.service.MemberService;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.member.vo.MemberLoginVo;
import top.ctong.gulimall.member.vo.MemberRegisterVo;


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
 * 会员
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-16 15:59:12
 */
@Slf4j
@RestController
@RequestMapping("member/member")
public class MemberController {

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private MemberService memberService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 会员注册
     * @param memberRegisterVo 会员信息
     * @return R
     * @author Clover You
     * @date 2022/2/10 10:03 下午
     */
    @PostMapping("/register")
    public R register(@RequestBody MemberRegisterVo memberRegisterVo) {
        boolean hasError = true;
        String errorMessage = null;
        Integer code = null;
        try {
            memberService.register(memberRegisterVo);
            hasError = false;
        } catch (MobileExistException e) {
            errorMessage = BizCodeEnum.MOBILE_EXIST_EXCEPTION.getMsg();
            code = BizCodeEnum.MOBILE_EXIST_EXCEPTION.getCode();
        } catch (UsernameExistException e) {
            errorMessage = BizCodeEnum.USERNAME_EXIST_EXCEPTION.getMsg();
            code = BizCodeEnum.USERNAME_EXIST_EXCEPTION.getCode();
        }
        if (hasError) {
            log.info("用户注册失败=====>> {}", errorMessage);
            return R.error(code, errorMessage);
        }
        return R.ok();
    }

    /**
     * 会员登录
     * @param memberLoginVo 登录凭证信息
     * @return R
     * @author Clover You
     * @date 2022/2/11 7:17 下午
     */
    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo memberLoginVo) {
        MemberEntity memberInfo = memberService.login(memberLoginVo);
        if (memberInfo == null) {
            BizCodeEnum bizCodeEnum = BizCodeEnum.LOGINACCT_PASSWORD_INVALID_EXCEPTION;
            return R.error(bizCodeEnum.getCode(), bizCodeEnum.getMsg());
        }
        return R.ok().setData(memberInfo);
    }
}
