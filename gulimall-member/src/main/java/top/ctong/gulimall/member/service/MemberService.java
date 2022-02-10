package top.ctong.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.member.entity.MemberEntity;
import top.ctong.gulimall.member.exception.MobileExistException;
import top.ctong.gulimall.member.exception.UsernameExistException;
import top.ctong.gulimall.member.vo.MemberRegisterVo;

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
 * Copyright 2021 Clover You.
 * <p>
 * 会员
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-16 15:59:12
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 会员注册
     *
     * @param memberRegisterVo 会员信息
     * @author Clover You
     * @date 2022/2/10 10:05 下午
     */
    void register(MemberRegisterVo memberRegisterVo)throws MobileExistException, UsernameExistException;

    /**
     * 检查邮箱是否存在
     * @param email 邮箱
     * @author Clover You
     * @date 2022/2/10 10:33 下午
     */
    void checkMobileUnique(String email) throws MobileExistException;

    /**
     * 检查用户名是否存在
     *
     * @param userName 用户名
     * @author Clover You
     * @date 2022/2/10 10:32 下午
     */
    void checkUserNameUnique(String userName) throws UsernameExistException;
}

