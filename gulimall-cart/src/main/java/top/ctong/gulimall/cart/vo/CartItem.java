package top.ctong.gulimall.cart.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
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
 * 购物车项
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-17 9:05 下午
 */
@Data
public class CartItem {

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
}
