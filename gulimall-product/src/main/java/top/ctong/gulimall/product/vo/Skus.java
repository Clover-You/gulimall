package top.ctong.gulimall.product.vo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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
 * 属性信息
 * </p>
 *
 * @author Clover You
 * @create 2021/12/9 09:56
 */
@Data
public class Skus {

    /**
     * 属性列表
     */
    private List<Attr> attr;
    /**
     * sku名称
     */
    private String skuName;
    /**
     * 价格
     */
    private BigDecimal price;
    /** 
     * sku标题
     */
    private String skuTitle;
    /** 
     * sku副标题
     */
    private String skuSubtitle;
    /** 
     * 当前版本图片
     */
    private List<Images> images;
    /** 
     * 属性笛卡尔积
     */
    private List<String> descar;
    /**
     * 满件减价格
     */
    private Integer fullCount;
    /**
     * 折扣
     */
    private BigDecimal discount;
    private Integer countStatus;
    /**
     * 满减价格
     */
    private BigDecimal fullPrice;
    /**
     * 满减后价格
     */
    private BigDecimal reducePrice;
    private Integer priceStatus;
    private List<MemberPrice> memberPrice;
}