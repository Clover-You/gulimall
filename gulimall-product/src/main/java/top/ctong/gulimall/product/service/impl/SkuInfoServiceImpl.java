package top.ctong.gulimall.product.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.product.dao.SkuInfoDao;
import top.ctong.gulimall.product.entity.SkuImagesEntity;
import top.ctong.gulimall.product.entity.SkuInfoEntity;
import top.ctong.gulimall.product.entity.SpuImagesEntity;
import top.ctong.gulimall.product.entity.SpuInfoDescEntity;
import top.ctong.gulimall.product.feign.SeckillFeignService;
import top.ctong.gulimall.product.service.*;
import top.ctong.gulimall.product.to.SeckillSkuRedisTo;
import top.ctong.gulimall.product.vo.SkuItemVo;


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
 * sku信息
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private SeckillFeignService seckillFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
            new Query<SkuInfoEntity>().getPage(params),
            new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 保存sku信息
     * @param skuInfoEntity sku信息
     * @author Clover You
     * @date 2021/12/9 16:32
     */
    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    /**
     * 根据自定义条件通过sku信息查询商品信息
     * @param params 自定义条件
     * @return PageUtils
     * @author Clover You
     * @date 2021/12/12 09:03
     */
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasText(key)) {
            wrapper.and(cms -> {
                cms.eq("spu_id", key).or().like("sku_name", key);
            });
        }
        String catelogId = (String) params.get("catelogId");
        if (StringUtils.hasText(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            wrapper.eq("catalog_id", catelogId);
        }
        String brandId = (String) params.get("brandId");
        if (StringUtils.hasText(brandId) && !"0".equalsIgnoreCase(brandId)) {
            wrapper.eq("brand_id", brandId);
        }
        String min = (String) params.get("min");
        if (StringUtils.hasText(min)) {
            try {
                BigDecimal mbd = new BigDecimal(min);
                if (mbd.compareTo(new BigDecimal(0)) > 0) {
                    wrapper.ge("price", min);
                }
            } catch (Exception e) {
                log.error("转换BigDecimal失败，非法字符串：" + min);
            }
        }
        String max = (String) params.get("max");
        if (StringUtils.hasText(max)) {
            try {
                BigDecimal mbd = new BigDecimal(max);
                if (mbd.compareTo(new BigDecimal(0)) > 0) {
                    wrapper.le("price", max);
                }
            } catch (Exception e) {
                log.error("转换BigDecimal失败，非法字符串：" + max);
            }
        }
        IPage<SkuInfoEntity> iPage = this.page(
            new Query<SkuInfoEntity>().getPage(params),
            wrapper
        );
        return new PageUtils(iPage);
    }

    /**
     * 通过规格id查询所有属性信息
     * @param spuId 规格id
     * @return List<SkuInfoEntity>
     * @author Clover You
     * @date 2021/12/22 09:45
     */
    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        List<SkuInfoEntity> list = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
        return Optional.of(list).orElse(new ArrayList<>());
    }

    /**
     * 通过id查询sku详细信息
     * @param skuId sku id
     * @return SkuItemVo
     * @author Clovou
     * @date 2022/2/4 8:03 下午
     */
    @Cacheable(key = "'skuId::' + #root.args[0]", cacheNames = "item")
    @Override
    public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        SkuItemVo skuItemVo = new SkuItemVo();
        CompletableFuture<SkuInfoEntity> future = CompletableFuture.supplyAsync(() -> {
            // 1. sku 基本信息 pms_sku_info
            SkuInfoEntity skuInfo = getById(skuId);
            skuItemVo.setSkuInfo(skuInfo);
            return skuInfo;
        }, threadPoolExecutor);

        CompletableFuture<Void> imgFuture = CompletableFuture.runAsync(() -> {
            // 2. sku 图片信息 pms_spu_images
            List<SkuImagesEntity> spuImagesEntities = skuImagesService.getImagesBySkuId(skuId);
            skuItemVo.setSkuImages(spuImagesEntities);
        }, threadPoolExecutor);

        CompletableFuture<Void> saleAttrFuture = future.thenAcceptAsync((res) -> {
            // 3. spu 销售属性组合
            List<SkuItemVo.ItemSaleAttrsVo> saleAttrs = skuSaleAttrValueService.getSaleAttrsBySpuId(res.getSpuId());
            skuItemVo.setSaleAttrs(saleAttrs);
        }, threadPoolExecutor);

        CompletableFuture<Void> spuDescFuture = future.thenAcceptAsync((res) -> {
            // 4. 获取spu介绍
            SpuInfoDescEntity spuDesc = spuInfoDescService.getById(res.getSpuId());
            skuItemVo.setSpuInfoDesc(spuDesc);
        }, threadPoolExecutor);

        CompletableFuture<Void> attrGroupFuture = future.thenAcceptAsync((res) -> {
            // 5. 获取spu规格参数信息
            List<SkuItemVo.SpuItemBaseAttrVo> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(
                res.getSpuId(),
                res.getCatalogId()
            );
            skuItemVo.setGroupAttrs(attrGroupVos);
        });

        // 获取商品秒杀信息
        CompletableFuture<Void> seckillFuture = CompletableFuture.runAsync(() -> {
            try {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                R skuSeckillInfo = seckillFeignService.getSkuSeckillInfo(skuId);
                if (skuSeckillInfo.getCode().equals(0)) {
                    SeckillSkuRedisTo data = skuSeckillInfo.getData(new TypeReference<SeckillSkuRedisTo>() {
                    });
                    skuItemVo.setSeckillInfo(data);
                }
            } catch (Exception e) {

            }

        }, threadPoolExecutor);

        CompletableFuture.allOf(future, imgFuture, saleAttrFuture, spuDescFuture, attrGroupFuture, seckillFuture).join();
        return skuItemVo;
    }
}