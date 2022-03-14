package top.ctong.gulimall.coupon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.ctong.gulimall.common.utils.Constant;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.coupon.dao.SeckillSessionDao;
import top.ctong.gulimall.coupon.entity.SeckillSessionEntity;
import top.ctong.gulimall.coupon.entity.SeckillSkuRelationEntity;
import top.ctong.gulimall.coupon.service.SeckillSessionService;
import top.ctong.gulimall.coupon.service.SeckillSkuRelationService;


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
 * 秒杀活动场次
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-16 15:44:41
 */
@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity>
    implements SeckillSessionService {

    @Autowired
    private SeckillSkuRelationService seckillSkuRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillSessionEntity> page = this.page(
            new Query<SeckillSessionEntity>().getPage(params),
            new QueryWrapper<SeckillSessionEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取最近三天活动的商品
     * @return List<SeckillSessionEntity> 商品列表
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/14 6:49 下午
     */
    @Override
    public List<SeckillSessionEntity> getLates3DaySession() {
        QueryWrapper<SeckillSessionEntity> wrapper = new QueryWrapper<>();
        LocalDate nowDate = LocalDate.now();
        // 拿到未来三天的日期
        LocalDate tomorrowDate = nowDate.plusDays(1);
        LocalDate threeDays = nowDate.plusDays(2);

        LocalTime minTime = LocalTime.MIN;
        LocalTime maxTime = LocalTime.MAX;


        String tdc = LocalDateTime.of(tomorrowDate, minTime)
            .format(DateTimeFormatter.ofPattern(Constant.DateUtils.YYYY_DD_MM_HH_MM_SS));

        String threeDc = LocalDateTime.of(threeDays, maxTime)
            .format(DateTimeFormatter.ofPattern(Constant.DateUtils.YYYY_DD_MM_HH_MM_SS));

        wrapper.between("start_time", tdc, threeDc);
        List<SeckillSessionEntity> list = this.list(wrapper);

        // 获取到所有活动所关联的商品
        List<SeckillSessionEntity> target = list.stream().peek((session) -> {
            QueryWrapper<SeckillSkuRelationEntity> relationWrapper = new QueryWrapper<>();
            relationWrapper.eq("promotion_session_id", session.getId());

            // 通过活动id查询关联的商品信息 promotion_session_id
            List<SeckillSkuRelationEntity> relationList = seckillSkuRelationService.list(relationWrapper);

            session.setRelation(relationList);
        }).collect(Collectors.toList());

        return target;
    }

}