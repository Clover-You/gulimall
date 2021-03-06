package top.ctong.gulimall.order.vo;

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
 * Copyright 2022 Clover You.
 * <p>
 * 购物项
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-25 1:46 PM
 */
@Data
public class OrderItemVo {

    /**
     * 商品id
     */
    private Long skuId;

    /**
     * 是否选中
     */
    private Boolean check = true;

    /**
     * 标题
     */
    private String title;

    /**
     * 商品封面
     */
    private String image;

    /**
     * 套餐属性
     */
    private List<String> skuAttr;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 商品数量
     */
    private Integer count;

    /**
     * 总计
     */
    private BigDecimal totalPrice;

    /**
     * 是否有货
     */
    private Boolean hasStock = true;

    /**
     * 重量(kg)
     */
    private BigDecimal weight;

    /**
     * 总价计算
     * @return BigDecimal
     * @author Clover You
     * @date 2022/2/17 9:11 下午
     */
    public BigDecimal getTotalPrice() {
        if (this.price == null || this.count == null) {
            return new BigDecimal(0);
        }
        return this.price.multiply(new BigDecimal(this.count));
    }

    /**
     * 商品重量
     * @return BigDecimal
     * @author Clover You
     * @date 2022/2/26 9:05 上午
     */
    public BigDecimal getWeight() {
        if (weight == null) {
            return new BigDecimal("0.00");
        }
        return weight;
    }
}
