package top.ctong.gulimall.coupon.service.impl;

import com.google.j2objc.annotations.WeakOuter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.ctong.gulimall.common.to.MemberPriceTo;
import top.ctong.gulimall.common.to.SkuReductionTo;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.coupon.dao.SkuFullReductionDao;
import top.ctong.gulimall.coupon.entity.MemberPriceEntity;
import top.ctong.gulimall.coupon.entity.SkuFullReductionEntity;
import top.ctong.gulimall.coupon.entity.SkuLadderEntity;
import top.ctong.gulimall.coupon.service.MemberPriceService;
import top.ctong.gulimall.coupon.service.SkuFullReductionService;
import top.ctong.gulimall.coupon.service.SkuLadderService;


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
 * 商品满减信息
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-16 15:44:41
 */
@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    private SkuLadderService skuLadderService;

    @Autowired
    private MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 保存sku优惠信息
     * @param skuReductionTo 优惠信息
     * @author Clover You
     * @date 2021/12/9 17:33
     */
    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {
        //保存SKU优惠信息 gulimall-sms -> sms_sku_ladder(打折表)、sms_sku_full_reduction（满减）、sms_member_price（会员价格）
        // 保存阶梯价格
        if (skuReductionTo.getFullCount() > 0) {
            SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
            BeanUtils.copyProperties(skuReductionTo, skuLadderEntity);

            skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
            skuLadderService.save(skuLadderEntity);
        }

        // 保存满减信息
        if (skuReductionTo.getFullPrice().compareTo(new BigDecimal(0)) == 1) {
            SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
            BeanUtils.copyProperties(skuReductionTo, skuFullReductionEntity);

            skuFullReductionEntity.setAddOther(skuReductionTo.getCountStatus());

            this.save(skuFullReductionEntity);
        }

        // 保存会员价格
        List<MemberPriceTo> memberPrice = skuReductionTo.getMemberPrice();
        List<MemberPriceEntity> mps = memberPrice.stream().filter(mp -> {
            return mp.getPrice().compareTo(new BigDecimal(0)) == 1;
        }).map(mp -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setMemberPrice(mp.getPrice());
            memberPriceEntity.setSkuId(skuReductionTo.getSkuId());
            memberPriceEntity.setMemberLevelId(mp.getId());
            memberPriceEntity.setAddOther(1);
            memberPriceEntity.setMemberLevelName(mp.getName());
            return memberPriceEntity;
        }).collect(Collectors.toList());

        memberPriceService.saveBatch(mps);
    }

}