package top.ctong.gulimall.common.to;

import lombok.Data;

import java.util.Date;

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
 * gitee 用户信息类型
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-13 12:31 上午
 */
@Data
public class GiteeUserInfo {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户空间名
     */
    private String login;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户头像地址
     */
    private String avatarUrl;

    /**
     * 信息快捷路径
     */
    private String url;

    /**
     * 用户主页地址
     */
    private String htmlUrl;

    /**
     *
     */
    private String remark;

    /**
     * 所有被关注用户地址
     */
    private String followersUrl;

    private String followingUrl;

    private String gistsUrl;

    private String starredUrl;

    private String subscriptionsUrl;

    private String organizationsUrl;

    private String reposUrl;

    private String eventsUrl;

    private String receivedEventsUrl;

    /**
     * 用户类型？
     */
    private String type;

    /**
     * 博客地址
     */
    private String blog;

    /**
     * 微博地址
     */
    private String weibo;

    /**
     * 个人介绍
     */
    private String bio;

    /**
     * 公开项目数量
     */
    private Integer publicRepos;

    private Integer publicGists;

    /**
     * 粉丝数
     */
    private Integer followers;

    /**
     * 关注数
     */
    private Integer following;

    /**
     * star数
     */
    private Integer stared;

    /**
     * 关注的仓库
     */
    private Integer watched;

    /**
     * 信息创建时间
     */
    private Date createdAt;

    /**
     * 信息修改时间
     */
    private Date updatedAt;

    /**
     * 邮箱
     */
    private String email;
}
