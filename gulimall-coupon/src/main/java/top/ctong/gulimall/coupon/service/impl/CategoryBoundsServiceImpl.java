package top.ctong.gulimall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.coupon.dao.CategoryBoundsDao;
import top.ctong.gulimall.coupon.entity.CategoryBoundsEntity;
import top.ctong.gulimall.coupon.service.CategoryBoundsService;


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
 * 商品分类积分设置
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-16 15:44:41
 */
@Service("categoryBoundsService")
public class CategoryBoundsServiceImpl extends ServiceImpl<CategoryBoundsDao, CategoryBoundsEntity> implements CategoryBoundsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBoundsEntity> page = this.page(
                new Query<CategoryBoundsEntity>().getPage(params),
                new QueryWrapper<CategoryBoundsEntity>()
        );

        return new PageUtils(page);
    }

}