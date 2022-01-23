package top.ctong.gulimall.common.vo;
import com.baomidou.mybatisplus.annotation.TableId;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import lombok.Data;
import top.ctong.gulimall.common.valid.ListValue;
import top.ctong.gulimall.common.valid.group.AggregationGroup;
import top.ctong.gulimall.common.valid.group.InsertGroup;
import top.ctong.gulimall.common.valid.group.UpdateGroup;
import top.ctong.gulimall.common.valid.group.UpdateStatusGroup;

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
 * 品牌信息
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-01-23 19:13
 */
@Data
public class BrandEntityVo {

    /**
     * 品牌id
     */
    @Null(message = "不能存在该字段", groups = {InsertGroup.class})
    @NotNull(message = "id不存在", groups = {UpdateGroup.class})
    @TableId
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名称不能为空", groups = {AggregationGroup.class})
    private String name;
    /**
     * 品牌logo地址
     */
    @NotEmpty(message = "logo地址不能为空", groups = {AggregationGroup.class})
    @URL(message = "logo必须是一个合法的url地址", groups = {AggregationGroup.class})
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @ListValue(value = {1, 0}, groups = {InsertGroup.class, UpdateStatusGroup.class})
    @NotNull(groups = {UpdateStatusGroup.class, InsertGroup.class}, message = "显示状态不能为空")
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotEmpty(message = "首字母不能为空", groups = {AggregationGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "首字母只能是一个字母", groups = {AggregationGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(message = "排序字段不能为空", groups = {AggregationGroup.class})
    @Min(value = 0, message = "只能是一个大于0的整数", groups = {AggregationGroup.class})
    private Integer sort;
}
