package top.ctong.gulimall.common.to.es;

import lombok.Data;

import java.io.Serializable;
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
 * sku在es中存储的模型
 * </p>
 * @author Clover You
 * @create 2021-12-22 09:24
 */
@Data
public class SkuEsModel implements Serializable {

    private static final long serialVersionUID = 1901000991670152905L;

    /**
     * skuId
     */
    private Long skuId;

    /**
     * spuId
     */
    private Long spuId;

    /**
     * 属性标题
     */
    private String skuTitle;

    /**
     * 价格
     */
    private BigDecimal skuPrice;

    /**
     * 默认图片
     */
    private String skuImg;

    /**
     * 销量
     */
    private Long saleCount;

    /**
     * 是否有库存
     */
    private Boolean hasStock;

    /**
     * 热门评分
     */
    private Long hotScore;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 分类id
     */
    private Long catalogId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌图片
     */
    private String brandImg;

    /**
     * 分类名称
     */
    private String catalogName;
    
    /** 
     * 规格属性信息
     */
    private List<Attr> attrs;

    @Data
    public static class Attr implements Serializable {

        private static final long serialVersionUID = -5832820069395971321L;

        /**
         * 属性id
         */
        private Long attrId;

        /**
         * 属性名
         */
        private String attrName;

        /**
         * 属性值
         */
        private String attrValue;
    }

}


/**
 * {
 * 	"mappings": {
 * 		"properties": {
 * 			"skuId": {
 * 				"type": "long"
 *                        },
 * 			"spuId": {
 * 				"type": "keyword"
 *            },
 * 			"skuTitle": {
 * 				"type": "text",
 * 				"analyzer": "ik_smart"
 *            },
 * 			"skuPrice": {
 * 				"type": "keyword"
 *            },
 * 			"skuImg": {
 * 				"type": "keyword",
 * 				"index": false,
 * 				"doc_values": false
 *            },
 * 			"saleCount": {
 * 				"type": "long"
 *            },
 * 			"hasStock": {
 * 				"type": "boolean"
 *            },
 * 			"hotScore": {
 * 				"type": "long"
 *            },
 * 			"brandId": {
 * 				"type": "long"
 *            },
 * 			"catalogId": {
 * 				"type": "long"
 *            },
 * 			"brandName": {
 * 				"type": "keyword",
 * 				"index": false,
 * 				"doc_values": false
 *            },
 * 			"brandImg": {
 * 				"type": "keyword",
 * 				"index": false,
 * 				"doc_values": false
 *            },
 * 			"catalogName": {
 * 				"type": "keyword",
 * 				"index": false,
 * 				"doc_values": false
 *            },
 * 			"attrs": {
 * 				"type": "nested",
 * 				"properties": {
 * 					"attrId": {
 * 						"type": "long"
 *                    },
 * 					"attrName": {
 *   					"type": "keyword",
 *   					"index": false,
 *   					"doc_values": false
 *                    },
 *   				"attrValue": {
 *   					"type": "keyword"
 *                }
 *                }
 *            } 		}
 * 	}
 * }
 */
