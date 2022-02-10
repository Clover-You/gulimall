package top.ctong.gulimall.member.dao;

import org.apache.ibatis.annotations.Param;
import top.ctong.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


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
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {

    /**
     * 检查手机号是否存在
     * @param phone 手机号
     * @return boolean
     * @author Clover You
     * @date 2022/2/11 2:02 上午
     */
    boolean checkMobileUnique(@Param("phone") String phone);

    /**
     * 检查用户名是否存在
     * @param userName 用户名
     * @return boolean
     * @author Clover You
     * @date 2022/2/11 2:02 上午
     */
    boolean checkUserNameUnique(@Param("userName") String userName);
}
