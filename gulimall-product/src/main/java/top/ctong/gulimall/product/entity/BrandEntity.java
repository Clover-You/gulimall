package top.ctong.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import top.ctong.gulimall.common.valid.ListValue;
import top.ctong.gulimall.common.valid.group.InsertGroup;
import top.ctong.gulimall.common.valid.group.AggregationGroup;
import top.ctong.gulimall.common.valid.group.UpdateGroup;

import javax.validation.constraints.*;


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
 * 品牌
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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
    @ListValue(value = {1, 0}, groups = {InsertGroup.class})
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotEmpty(message = "首字母不能为空", groups = {AggregationGroup.class})
    @Pattern(regexp = "/[^a-zA-Z$]/", message = "首字母只能是一个字母", groups = {AggregationGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(message = "排序字段不能为空", groups = {AggregationGroup.class})
    @Min(value = 0, message = "只能是一个大于0的整数", groups = {AggregationGroup.class})
    private Integer sort;

}
