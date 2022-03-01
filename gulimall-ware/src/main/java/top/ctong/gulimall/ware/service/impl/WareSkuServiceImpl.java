package top.ctong.gulimall.ware.service.impl;

import com.google.gson.Gson;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.ctong.gulimall.common.to.SkuInfoTo;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.ware.dao.WareSkuDao;
import top.ctong.gulimall.ware.entity.WareSkuEntity;
import top.ctong.gulimall.ware.exception.NoStockException;
import top.ctong.gulimall.ware.feign.ProductFeignService;
import top.ctong.gulimall.ware.service.WareSkuService;
import top.ctong.gulimall.ware.vo.LockStockResultVo;
import top.ctong.gulimall.ware.vo.OrderItemVo;
import top.ctong.gulimall.ware.vo.SkuHasStockVo;
import top.ctong.gulimall.ware.vo.WareSkuLockVo;


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
 * 商品库存
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-16 16:12:36
 */
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if (StringUtils.hasText(skuId) && !"0".equalsIgnoreCase(skuId)) {
            queryWrapper.eq("sku_id", skuId);
        }
        String wareId = (String) params.get("wareId");
        if (StringUtils.hasText(wareId) && !"0".equalsIgnoreCase(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }
        IPage<WareSkuEntity> page = this.page(
            new Query<WareSkuEntity>().getPage(params),
            queryWrapper
        );

        return new PageUtils(page);
    }

    /**
     * 添加库存
     * @param skuId sku id
     * @param wareId 仓库id
     * @param skuNum 添加的数量
     * @author Clover You
     * @date 2021/12/13 16:05
     */
    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        // 判断如果没有这个库存信息则新增
        WareSkuEntity wareSku = this.getOne(
            new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId),
            false
        );
        if (wareSku == null) {
            WareSkuEntity entity = new WareSkuEntity();
            entity.setSkuId(skuId);
            entity.setWareId(wareId);
            entity.setStock(skuNum);
            entity.setStockLocked(0);
            // 查询sku名字
            R info = productFeignService.skuInfo(skuId);
            if (info.getCode() != 0) {
                log.error(info.getMsg());
            } else {
                Object skuInfo = info.get("skuInfo");
                Gson gson = new Gson();
                SkuInfoTo skuInfoTo = gson.fromJson(gson.toJson(skuInfo), SkuInfoTo.class);
                entity.setSkuName(skuInfoTo.getSkuName());
            }

            this.baseMapper.insert(entity);
        } else {
            this.baseMapper.addStock(skuId, wareId, skuNum);
        }
    }

    /**
     * 检查sku是否有库存
     * @param skuIds sku id列表
     * @return List<SkuHasStockVo>
     * @author Clover You
     * @date 2021/12/22 10:45
     */
    @Override
    public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {
        return skuIds.stream().map(id -> {
            Long count = this.baseMapper.getSkuStock(id);
            SkuHasStockVo vo = new SkuHasStockVo();
            vo.setSkuId(id);
            vo.setHasStock(Optional.ofNullable(count).orElse(0L) > 0);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 根据指定信息锁定库存
     * @param vo 商品锁定信息
     * @return Boolean
     * @author Clover You
     * @date 2022/2/27 8:35 下午
     */
    @Transactional
    @Override
    public Boolean orderLockStock(WareSkuLockVo vo) throws NoStockException{
//        List<LockStockResultVo> lr = new ArrayList<>(vo.getLocks().size());
        List<OrderItemVo> locks = vo.getLocks();
        // 封装库存信息
        List<SkuWareHasStock> hasStockWares = locks.stream().map((item) -> {
            SkuWareHasStock hasStock = new SkuWareHasStock();
            hasStock.setSkuId(item.getSkuId());
            // 检查哪个仓库有库存
            List<Long> ids = listWareIdHasSkuStock(item.getSkuId());
            hasStock.setWareIds(ids);
            hasStock.setLockCount(item.getCount());
            return hasStock;
        }).collect(Collectors.toList());

        // 库存锁定状态
//        boolean allLockedFlag = true;
        // 锁定库存
        for (SkuWareHasStock hasStockWare : hasStockWares) {
            List<Long> wareIds = hasStockWare.getWareIds();
            Long skuId = hasStockWare.getSkuId();
            if (CollectionUtils.isEmpty(wareIds)) {
                throw new NoStockException("skuId:" + skuId + " ==> 库存不足", skuId);
            }
            // 但前sku库存是否已锁定成功
            boolean lockedFlag = false;
            for (Long wareId : wareIds) {
                Long affectedCount = baseMapper.lockSkuStock(skuId, wareId, hasStockWare.getLockCount());
                if (affectedCount == 0) {
                    continue;
                }
                lockedFlag = true;
                break;
            }
            if (!lockedFlag) {
//                allLockedFlag = false;
                throw new NoStockException("skuId:" + skuId + " ==> 库存不足", skuId);
            }
        }
        return true;
    }

    /**
     * 查询该商品在哪有库存
     * @param skuId 商品id
     * @return List<Long>
     * @author Clover You
     * @date 2022/3/1 8:09 上午
     */
    @Override
    public List<Long> listWareIdHasSkuStock(Long skuId) {
        return baseMapper.listWareIdHasSkuStock(skuId);
    }

    /**
     * 有库存的仓库信息封装
     * @author Clover You
     * @date 2022/2/27 8:52 下午
     */
    @Data
    static class SkuWareHasStock {

        /**
         * 商品id
         */
        private Long skuId;

        /**
         * 库存id
         */
        private List<Long> wareIds;

        /**
         * 锁定件数
         */
        private Integer lockCount;
    }
}