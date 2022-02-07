package top.ctong.gulimall.product.vo;

import com.aliyuncs.kms.model.v20160120.ListAliasesRequest;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.ctong.gulimall.product.entity.SkuImagesEntity;
import top.ctong.gulimall.product.entity.SkuInfoEntity;
import top.ctong.gulimall.product.entity.SpuInfoDescEntity;

import java.io.Serializable;
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
 * 商品详情
 * </p>
 *
 * @author Clover You
 * @create 2022-02-04 8:02 下午
 */
@ToString
@Data
@Accessors
public class SkuItemVo {

    /**
     * sku 信息
     */
    private SkuInfoEntity skuInfo;

    /**
     * 是否有库存
     */
    Boolean hasStock = true;

    /**
     * 图片信息
     */
    private List<SkuImagesEntity> skuImages;

    /**
     * spu描述信息
     */
    private SpuInfoDescEntity spuInfoDesc;

    /**
     * 销售属性
     */
    private List<ItemSaleAttrsVo> saleAttrs;

    /**
     * 基本属性
     */
    private List<SpuItemBaseAttrVo> groupAttrs;

    @ToString
    @Data
    public static class ItemSaleAttrsVo {

        /**
         * 属性id
         */
        private Long attrId;

        /**
         * 属性名
         */
        private String attrName;

        /**
         * 属性值对应的sku id信息
         */
        private List<AttrValueWithSkuIdVo> valueWithSkuIdVos;

        @Data
        public static class AttrValueWithSkuIdVo {

            /**
             * 属性值
             */
            private String attrValues;

            /**
             * sku id
             */
            private String skuIds;
        }
    }

    @ToString
    @Data
    public static class SpuItemBaseAttrVo {

        /**
         * 分组名称
         */
        private String groupName;

        /**
         * 属性信息
         */
        private List<SpuBaseAttrVo> attrs;
    }

    @ToString
    @Data
    public static class SpuBaseAttrVo {

        /**
         * 属性名称
         */
        private String attrName;

        /**
         * 属性值
         */
        private String attrValue;
    }

}
