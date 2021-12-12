package top.ctong.gulimall.common.to;

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
 * sku优惠信息
 * </p>
 * @author Clover You
 * @create 2021-12-09 17:13
 */
@Data
public class SkuReductionTo {

    private Long skuId;
    /**
     * 满件减价格
     */
    private Integer fullCount;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 是否参与其他优惠
     */
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

    /**
     * 会员价格
     */
    private List<MemberPriceTo> memberPrice;

    @Override
    public String toString() {
        return "SkuReductionTo{" +
                "skuId=" + skuId +
                ", fullCount=" + fullCount +
                ", discount=" + discount +
                ", countStatus=" + countStatus +
                ", fullPrice=" + fullPrice +
                ", reducePrice=" + reducePrice +
                ", priceStatus=" + priceStatus +
                ", memberPrice=" + memberPrice +
                '}';
    }
}
