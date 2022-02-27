package top.ctong.gulimall.product.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.ctong.gulimall.common.constant.ProductConstant;
import top.ctong.gulimall.common.feign.CouponFeignService;
import top.ctong.gulimall.common.feign.SearchFeignService;
import top.ctong.gulimall.common.feign.WareFeignService;
import top.ctong.gulimall.common.to.MemberPriceTo;
import top.ctong.gulimall.common.to.SkuHasStockVo;
import top.ctong.gulimall.common.to.SkuReductionTo;
import top.ctong.gulimall.common.to.SpuBoundTo;
import top.ctong.gulimall.common.to.es.SkuEsModel;
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

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WareFeignService wareFeignService;

    @Autowired
    private SearchFeignService searchFeignService;

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

    /**
     * 根据条件查询SPU信息
     * @param params 自定义条件
     * @return PageUtils
     * @author Clover You
     * @date 2021/12/12 08:28
     */
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasText(key)) {
            wrapper.and(csm -> {
                // select ... where (id=? or spu_name like '%'+ ? +'%')
                csm.eq("id", key).or().like("spu_name", key);
            });
        }
        String status = (String) params.get("status");
        if (StringUtils.hasText(status)) {
            // select ... where publish_status=?
            wrapper.eq("publish_status", status);
        }
        String brandId = (String) params.get("brandId");
        if (StringUtils.hasText(brandId) && !"0".equalsIgnoreCase(brandId)) {
            // select ... where brand_id = ?
            wrapper.eq("brand_id", brandId);
        }
        String catelogId = (String) params.get("catelogId");
        if (StringUtils.hasText(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            // select ... where catalog_id = ?
            wrapper.eq("catalog_id", catelogId);
        }
        IPage<SpuInfoEntity> iPage = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(iPage);
    }

    /**
     * 商品上架
     * @param spuId 规格id
     * @author Clover You
     * @date 2021/12/22 09:17
     */
    @Override
    public void up(Long spuId) {
        // 查询当前sku所有可检索的规格属性
        List<ProductAttrValueEntity> attrValueEntityList = productAttrValueService.baseAttrListForSpu(spuId);
        List<Long> attrIds = attrValueEntityList.stream()
                .map(ProductAttrValueEntity::getAttrId).collect(Collectors.toList());
        List<Long> attrList = attrService.selectSearchAttrs(attrIds);
        Set<Long> attrIdSet = new HashSet<>(attrList);
        List<SkuEsModel.Attr> skuAttrEsModelList = attrValueEntityList.stream()
                .filter(av -> attrIdSet.contains(av.getAttrId()))
                .map(av -> {
                    SkuEsModel.Attr attr = new SkuEsModel.Attr();
                    BeanUtils.copyProperties(av, attr);
                    return attr;
                })
                .collect(Collectors.toList());

        // 查出spu所对应的所有SKU信息、品牌信息。。。
        List<SkuInfoEntity> skuInfoEntityList = skuInfoService.getSkusBySpuId(spuId);
        List<Long> skuIds = skuInfoEntityList.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());

        //  查询库存系统查询是否有库存
        Map<Long, SkuHasStockVo> skuHasStockVoMap = null;
        try {

            R skuHasStock = wareFeignService.getSkuHasStock(skuIds);
            TypeReference<List<SkuHasStockVo>> ref = new TypeReference<List<SkuHasStockVo>>() {};
            skuHasStockVoMap = skuHasStock.getData(ref).stream()
                    .collect(Collectors.toMap(SkuHasStockVo::getSkuId, v -> v));
        } catch (Exception e) {
            log.error("查询库存系统异常：{}", e);
        }

        // 封装每个SKU信息
        Map<Long, SkuHasStockVo> finalSkuHasStockVoMap = skuHasStockVoMap;
        List<SkuEsModel> esModelList = skuInfoEntityList.stream().map(skuInfo -> {
            // 组装ES的数据
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(skuInfo, skuEsModel);
            skuEsModel.setSkuPrice(skuInfo.getPrice());
            skuEsModel.setSkuImg(skuInfo.getSkuDefaultImg());

            // 设置是否有库存
            if (finalSkuHasStockVoMap != null) {
                SkuHasStockVo hasStock = finalSkuHasStockVoMap.get(skuInfo.getSkuId());
                skuEsModel.setHasStock(hasStock.getHasStock());
            } else {
                skuEsModel.setHasStock(true);
            }

            // 热度评分，默认0
            skuEsModel.setHotScore(0L);

            // 设置品牌和分类信息
            BrandEntity brand = brandService.getById(skuInfo.getBrandId());
            skuEsModel.setBrandName(brand.getName());
            skuEsModel.setBrandImg(brand.getLogo());

            CategoryEntity category = categoryService.getById(skuInfo.getCatalogId());
            skuEsModel.setCatalogName(category.getName());

            // 设置检索属性
            skuEsModel.setAttrs(skuAttrEsModelList);
            return skuEsModel;
        }).collect(Collectors.toList());

        // 保存到ES
        R r = searchFeignService.productStatusUp(esModelList);
        if (r.getCode() == 0) {
            // 修改当前spu状态
            this.baseMapper.updateStatus(spuId, ProductConstant.SpuStatusEnum.SPU_UP.getCode());
        }
    }

    /**
     * 根据skuId 查询 spu信息
     * @param skuId 商品id
     * @return SpuInfoEntity
     * @author Clover You
     * @date 2022/2/27 3:17 下午
     */
    @Override
    public SpuInfoEntity getSpuInfoBySkuId(Long skuId) {
        SkuInfoEntity skuInfo = skuInfoService.getById(skuId);
        Long spuId = skuInfo.getSpuId();
        SpuInfoEntity spuInfoEntity = baseMapper.selectOne(
            new QueryWrapper<SpuInfoEntity>().eq("id", spuId)
        );
        return spuInfoEntity;
    }
}
