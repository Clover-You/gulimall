package top.ctong.gulimall.product.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.ctong.gulimall.common.feign.CouponFeignService;
import top.ctong.gulimall.common.to.MemberPriceTo;
import top.ctong.gulimall.common.to.SkuReductionTo;
import top.ctong.gulimall.common.to.SpuBoundTo;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.product.dao.SpuInfoDao;
import top.ctong.gulimall.product.entity.*;
import top.ctong.gulimall.product.service.*;
import top.ctong.gulimall.product.vo.*;


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
 * spu信息
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 保存spu信息
     * @param vo spu信息
     * @author Clover You
     * @date 2021/12/9 10:08
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        // 保存spu基本信息 pms_spu_info
        SpuInfoEntity spuInfo = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, spuInfo);
        spuInfo.setCreateTime(new Date());
        spuInfo.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfo);

        // 保存SPU描述图片 pms_spu_info_desc
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(spuInfo.getId());
        descEntity.setDecript(String.join(",", decript));
        spuInfoDescService.saveSpuInfoDesc(descEntity);

        // 保存SPU图片集 pms_spu_images
        List<String> images = vo.getImages();
        spuImagesService.saveImagesByUrl(spuInfo.getId(), images);

        // 保存SPU规格参数 pms_product_attr_value
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> attrValueEntityList = baseAttrs.stream().map(attr -> {
            // 查询属性信息
            AttrEntity attrInfo = attrService.getById(attr.getAttrId());

            ProductAttrValueEntity attrValueEntity = new ProductAttrValueEntity();
            attrValueEntity.setAttrValue(attr.getAttrValues());
            attrValueEntity.setSpuId(spuInfo.getId());
            attrValueEntity.setAttrName(attrInfo.getAttrName());
            attrValueEntity.setQuickShow(attr.getShowDesc());
            return attrValueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveProductAttr(attrValueEntityList);

        // 保存SKU积分信息 gulimall-sms -> sms_spu_bounds
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        Bounds bounds = vo.getBounds();
        BeanUtils.copyProperties(bounds, spuBoundTo);
        spuBoundTo.setSpuId(spuInfo.getId());

        R sbr = couponFeignService.saveSpuBounds(spuBoundTo);
        if (sbr.getCode() != 0) {
            log.error("远程服务保存SPU积分信息失败：" + spuBoundTo.toString());
        }

        //  保存SKU基本信息 pms_sku_info
        List<Skus> skus = vo.getSkus();
        if (skus != null && !skus.isEmpty()) {
            for (Skus sku : skus) {
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(sku, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfo.getBrandId());
                skuInfoEntity.setCatalogId(spuInfo.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfo.getId());


                // 查找默认图片
                List<Images> skuImages = sku.getImages();
                for (Images image : skuImages) {
                    if (image.getDefaultImg() == 1) {
                        skuInfoEntity.setSkuDefaultImg(image.getImgUrl());
                    }
                }
                skuInfoService.saveSkuInfo(skuInfoEntity);

                // 保存SPU所有SKU信息

                //  保存SKU图片信息 pms_sku_images
                List<SkuImagesEntity> imageEntitys = skuImages.stream().filter(image -> {
                    return StringUtils.hasText(image.getImgUrl());
                }).map(image -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                    skuImagesEntity.setImgUrl(image.getImgUrl());
                    skuImagesEntity.setDefaultImg(image.getDefaultImg());
                    return skuImagesEntity;
                }).collect(Collectors.toList());
                skuImagesService.saveBatch(imageEntitys);

                //  保存SKU销售属性 pms_sku_sale_attr_value
                List<Attr> attrs = sku.getAttr();
                List<SkuSaleAttrValueEntity> saleAttrValueEntities = attrs.stream().map(attr -> {
                    SkuSaleAttrValueEntity attrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(attr, attrValueEntity);
                    return attrValueEntity;
                }).collect(Collectors.toList());

                skuSaleAttrValueService.saveBatch(saleAttrValueEntities);

                //  保存SKU优惠信息 gulimall-sms -> sms_sku_ladder(打折表)、sms_sku_full_reduction（满减）、sms_member_price（会员价格）
                if (sku.getFullPrice().compareTo(new BigDecimal(0)) > 0 || sku.getFullCount() > 0) {
                    SkuReductionTo skuReductionTo = new SkuReductionTo();
                    BeanUtils.copyProperties(sku, skuReductionTo);
                    skuReductionTo.setSkuId(skuInfoEntity.getSkuId());
                    List<MemberPriceTo> mbpts = sku.getMemberPrice().stream().map(mp -> {
                        MemberPriceTo mpt = new MemberPriceTo();
                        BeanUtils.copyProperties(mp, mpt);
                        return mpt;
                    }).collect(Collectors.toList());
                    skuReductionTo.setMemberPrice(mbpts);
                    R srr = couponFeignService.saveSkuReduction(skuReductionTo);
                    if (srr.getCode() != 0) {
                        log.error("远程服务保存SKU优惠信息失败：" + skuReductionTo.toString());
                    }
                }
            }
        }
    }

    /**
     * 保存SPU基本信息
     * @param spuInfo spu信息
     * @author Clover You
     * @date 2021/12/9 10:36
     */
    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfo) {
        this.baseMapper.insert(spuInfo);
    }

}