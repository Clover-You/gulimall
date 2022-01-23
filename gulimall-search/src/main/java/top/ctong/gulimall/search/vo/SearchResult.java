package top.ctong.gulimall.search.vo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import top.ctong.gulimall.common.to.es.SkuEsModel;

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
 * 检索结果
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-01-17 00:58
 */
@Data
public class SearchResult implements Serializable {

    private static final long serialVersionUID = -6786791090227051472L;

    /**
     * 检索到的所有商品信息
     */
    private List<SkuEsModel> product;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 页码列表
     */
    private List<Integer> pageNavs;

    /**
     * 面包屑数据
     */
    private List<Nav> navs = new ArrayList<>();

    /**
     * 已选属性的id
     */
    private List<Long> attrIds = new ArrayList<>();

    /**
     * 数据总数
     */
    private Long total;

    /**
     * 总页码
     */
    private Long totalPage;

    /**
     * 当前检索的结果所涉及的所有品牌
     */
    private List<Brand> brands;

    /**
     * 当前检索的结果所涉及到的所有属性
     */
    private List<Attr> attrs;

    /**
     * 当前检索的结果所涉及到的所有分类
     */
    private List<Catalog> catalogs;

    /**
     * 当前检索的结果所涉及到的所有品牌
     *
     * @author Clover You
     * @date 2022/1/17 01:13
     */
    @Data
    public static class Brand {

        /**
         * 品牌id
         */
        private Long brandId;

        /**
         * 品牌名称
         */
        private String brandName;

        /**
         * 品牌图片
         */
        private String brandImg;

    }

    /**
     * 当前检索的结果所涉及到的所有属性
     *
     * @author Clover You
     * @date 2022/1/17 01:14
     */
    @Data
    public static class Attr {

        /**
         * 属性id
         */
        private Long attrId;

        /**
         * 属性名称
         */
        private String attrName;

        /**
         * 属性值
         */
        private List<String> attrValue;

    }

    /**
     * 当前检索的结果所涉及到的分类
     *
     * @author Clover You
     * @date 2022/1/17 01:14
     */
    @Data
    public static class Catalog {

        /**
         * 分类id
         */
        private Long catalogId;

        /**
         * 分类名称
         */
        private String catalogName;

    }

    /**
     * 导航信息
     *
     * @author Clover You
     * @date 2022/1/23 16:15
     */
    @Data
    public static class Nav {

        /**
         * 导航名
         */
        private String navName;

        /**
         * 值
         */
        private String navValue;

        /**
         * 跳转的位置
         */
        private String link;

    }

}
