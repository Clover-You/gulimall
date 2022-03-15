package top.ctong.gulimall.ware.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import top.ctong.gulimall.common.constant.WareConstant;
import top.ctong.gulimall.common.exception.BizCodeEnum;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.ware.dao.PurchaseDao;
import top.ctong.gulimall.ware.entity.PurchaseDetailEntity;
import top.ctong.gulimall.ware.entity.PurchaseEntity;
import top.ctong.gulimall.ware.exception.HandlerExceptionReJSON;
import top.ctong.gulimall.ware.service.PurchaseDetailService;
import top.ctong.gulimall.ware.service.PurchaseService;
import top.ctong.gulimall.ware.service.WareSkuService;
import top.ctong.gulimall.ware.vo.MergeVo;
import top.ctong.gulimall.ware.vo.PurchaseDoneVo;
import top.ctong.gulimall.ware.vo.PurchaseItemDoneVo;


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
 * 采购信息
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-16 16:12:37
 */
@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Autowired
    private WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> queryWrapper = new QueryWrapper<>();

        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    /**
     * 查询未领取的采购单
     * @param params 分页数据
     * @return PageUtils
     * @author Clover You
     * @date 2021/12/12 20:39
     */
    @Override
    public PageUtils queryPageUnreceive(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>().eq("status", 0).or().eq("status", 1)
        );
        return new PageUtils(page);
    }

    /**
     * 合并采购单
     * @param merge 采购单信息
     * @author Clover You
     * @date 2021/12/13 09:06
     */
    @Transactional
    @Override
    public void mergePurchase(MergeVo merge) throws HandlerExceptionReJSON {
        Long purchaseId = merge.getPurchaseId();
        // 如果 purchaseId 是空的，那么需要新建一个 purchase 单
        if (purchaseId == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getStatus());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        } else {
            PurchaseEntity byId = this.getById(purchaseId);
            boolean isCreated = byId.getStatus().equals(WareConstant.PurchaseStatusEnum.CREATED.getStatus());
            boolean isAssigned = byId.getStatus().equals(WareConstant.PurchaseStatusEnum.ASSIGNED.getStatus());
            if (!(isCreated || isAssigned)) {
                throw new HandlerExceptionReJSON("只能合并新建或已分配的订单", BizCodeEnum.ILLEGAL_OPERATION.getCode());
            }
        }

        List<Long> items = merge.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> pdes = items.stream().map(id -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(id);
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getStatus());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        // 批量保存
        purchaseDetailService.updateBatchById(pdes);

        // 更新采购单修改时间
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseId);
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);
    }

    /**
     * 领取采购单
     * @param purchaseIds 采购单id
     * @author Clover You
     * @date 2021/12/13 10:00
     */
    @Transactional
    @Override
    public void received(List<Long> purchaseIds) {
        // 确认当前采购单是新建或已分配状态
        List<PurchaseEntity> purchaseEntityList = purchaseIds.stream().map(id -> {
            return this.getById(id);
        }).filter(item -> {
            return item != null && (WareConstant.PurchaseStatusEnum.CREATED.getStatus() == item.getStatus()
                    || WareConstant.PurchaseStatusEnum.ASSIGNED.getStatus() == item.getStatus());
        }).map(item -> {
            item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getStatus());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());

        // 改变采购单的状态
        this.updateBatchById(purchaseEntityList);

        // 改变采购项的状态
        Optional.of(purchaseEntityList).orElse(new ArrayList<>()).forEach(entity -> {
            List<PurchaseDetailEntity> detailList = purchaseDetailService.listDetailByPurchaseId(entity.getId());
            List<PurchaseDetailEntity> newDetail = detailList.stream().map(item -> {
                PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.BUYING.getStatus());
                purchaseDetailEntity.setId(item.getId());
                return purchaseDetailEntity;
            }).collect(Collectors.toList());
            purchaseDetailService.updateBatchById(newDetail);
        });
    }

    /**
     * 完成采购
     * @param vo 采购信息
     * @author Clover You
     * @date 2021/12/13 15:41
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void done(PurchaseDoneVo vo) {
        // 改变采购项的状态
        // 是否有失败
        boolean flag = true;
        List<PurchaseItemDoneVo> items = vo.getItems();
        List<PurchaseDetailEntity> updates = new ArrayList<>(items.size());
        for (PurchaseItemDoneVo item : items) {
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
            detailEntity.setId(item.getItemId());
            if (item.getStatus() == WareConstant.PurchaseDetailStatusEnum.HASERROR.getStatus()) {
                flag = false;
                detailEntity.setStatus(item.getStatus());
            } else {
                detailEntity.setStatus(WareConstant.PurchaseDetailStatusEnum.FINISH.getStatus());

                // 将成功采购的进行入库
                // 查询当前采购项详细信息
                PurchaseDetailEntity detailInfo = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock(detailInfo.getSkuId(),detailInfo.getWareId(), detailInfo.getSkuNum());
            }
            updates.add(detailEntity);

        }
        purchaseDetailService.updateBatchById(updates);

        // 改变采购单状态
        Long purchaseId = vo.getId();
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseId);
        purchaseEntity.setUpdateTime(new Date());
        purchaseEntity.setStatus(flag ?
                WareConstant.PurchaseStatusEnum.FINISH.getStatus() :
                WareConstant.PurchaseStatusEnum.HASERROR.getStatus()
        );
        // 保存采购单信息修改
        this.updateById(purchaseEntity);

    }
}