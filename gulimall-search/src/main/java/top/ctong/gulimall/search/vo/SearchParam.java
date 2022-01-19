package top.ctong.gulimall.search.vo;
import java.io.Serializable;
import java.util.List;

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
 * 检索条件
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-01-17 00:30
 */
@Data
public class SearchParam implements Serializable {

    private static final long serialVersionUID = 6699495679074731167L;

    /**
     * 全文检索
     */
    private String keyword;

    /**
     * 三级分类id
     */
    private Long catalog3Id;

    /**
     * 排序条件
     */
    private String sort;

    /**
     * 是否有货
     */
    private Integer hasStock;

    /**
     * 价格区间
     */
    private String skuPrice;

    /**
     * 品牌id
     */
    private List<Long> brandId;

    /**
     * 属性信息
     */
    private List<String> attrs;

    /**
     * 页码
     */
    private Integer pageNum = 1;

}
