package top.ctong.gulimall.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.ctong.gulimall.common.to.GiteeUserInfo;
import top.ctong.gulimall.common.utils.BCryptPasswordEncoders;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.member.dao.MemberDao;
import top.ctong.gulimall.member.entity.MemberEntity;
import top.ctong.gulimall.member.entity.MemberLevelEntity;
import top.ctong.gulimall.member.exception.MobileExistException;
import top.ctong.gulimall.member.exception.UsernameExistException;
import top.ctong.gulimall.member.service.MemberLevelService;
import top.ctong.gulimall.member.service.MemberService;
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
@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberLevelService memberLevelService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
            new Query<MemberEntity>().getPage(params),
            new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * 会员注册
     * @param memberRegisterVo 会员信息
     * @author Clover You
     * @date 2022/2/10 10:05 下午
     */
    @Override
    public void register(MemberRegisterVo memberRegisterVo) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUsername(memberRegisterVo.getUserName());
        // 使用spring加密密码
        memberEntity.setPassword(BCryptPasswordEncoders.encode(memberRegisterVo.getPassword()));
        // 查询默认会员
        MemberLevelEntity defaultLevel = memberLevelService.getDefaultLevel();
        memberEntity.setLevelId(defaultLevel.getId());
        memberEntity.setMobile(memberRegisterVo.getPhone());

        // 检查手机号和用户名是否被占用
        if (memberRegisterVo.getPhone() != null) {
            checkMobileUnique(memberRegisterVo.getPhone());
        }
        checkUserNameUnique(memberRegisterVo.getUserName());
        baseMapper.insert(memberEntity);
    }

    /**
     * 检查邮箱是否存在
     * @param phone 邮箱
     * @author Clover You
     * @date 2022/2/10 10:33 下午
     */
    @Override
    public void checkMobileUnique(String phone) throws MobileExistException {
        if (baseMapper.checkMobileUnique(phone)) {
            throw new MobileExistException();
        }
    }

    /**
     * 检查用户名是否存在
     * @param userName 用户名
     * @author Clover You
     * @date 2022/2/10 10:32 下午
     */
    @Override
    public void checkUserNameUnique(String userName) throws UsernameExistException {
        if (baseMapper.checkUserNameUnique(userName)) {
            throw new UsernameExistException();
        }
    }

    /**
     * 用户登录
     * @param memberLoginVo 用户信息
     * @return MemberEntity 用户信息
     * @author Clover You
     * @date 2022/2/11 7:44 下午
     */
    @Override
    public MemberEntity login(MemberLoginVo memberLoginVo) {
        String loginAcct = memberLoginVo.getLoginAcct();
        String password = memberLoginVo.getPassword();

        QueryWrapper<MemberEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.or().eq("username", loginAcct)
            .or().eq("mobile", loginAcct);
        MemberEntity memberInfo = baseMapper.selectOne(queryWrapper);
        if (memberInfo == null) {
            return null;
        }

        String metaPass = memberInfo.getPassword();
        boolean matches = BCryptPasswordEncoders.matches(password, metaPass);
        if (!matches) {
            return null;
        }
        return memberInfo;
    }

    /**
     * gitee用户登录，如果用户不存在则创建新用户并捆绑当前gitee
     * @param userInfo gitee信息
     * @return MemberEntity
     * @author Clover You
     * @date 2022/2/13 12:59 上午
     */
    @Override
    public MemberEntity giteeLogin(GiteeUserInfo userInfo) {
        // gitee用户id
        Long id = userInfo.getId();

        MemberEntity memberEntity = baseMapper.selectOne(
            new QueryWrapper<MemberEntity>().eq("gitee_id", id)
        );
        if (memberEntity != null) {
            return memberEntity;
        }

        // 用户注册
        memberEntity = new MemberEntity();
        memberEntity.setNickname(userInfo.getName());
        memberEntity.setGiteeId(id);

        MemberLevelEntity level = memberLevelService.getDefaultLevel();
        memberEntity.setLevelId(level.getId());

        memberEntity.setCreateTime(new Date());
        memberEntity.setStatus(1);
        memberEntity.setGrowth(0);
        memberEntity.setHeader(userInfo.getAvatarUrl());
        memberEntity.setSign(userInfo.getBio());
        memberEntity.setGender(1);
        baseMapper.insert(memberEntity);

        return memberEntity;
    }
}