package top.ctong.gulimall.auth.po;

import lombok.Data;

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
 * gitee 社交登录
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-12 10:27 下午
 */
@Data
public class GiteeSocialUser {
    /**
     * token
     */
    private String accessToken;

    /**
     * token 类型
     */
    private String tokenType;

    /**
     * 过期时间
     */
    private Integer expiresIn;

    /**
     * 刷新 accessToken 所需携带的token
     */
    private String refreshToken;

    /**
     * access作用范围
     */
    private String scope;

    /**
     * 创建时间
     */
    private Long createdAt;
}
