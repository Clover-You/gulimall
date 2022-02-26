package top.ctong.gulimall.ware.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.common.utils.R;
import top.ctong.gulimall.ware.dao.WareInfoDao;
import top.ctong.gulimall.ware.entity.WareInfoEntity;
import top.ctong.gulimall.ware.feign.MemberFeignService;
import top.ctong.gulimall.ware.service.WareInfoService;
import top.ctong.gulimall.ware.to.MemberAddressTo;
import top.ctong.gulimall.ware.vo.FareVo;


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
 * 仓库信息
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-16 16:12:37
 */
@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Autowired
    private MemberFeignService memberFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<WareInfoEntity> queryWrapper = new QueryWrapper<>();
        // 通过检索条件模糊检索
        if (StringUtils.hasText(key)) {
            // select ... where id=? or name like '%?%' or address like '%?%' or areacode like '%?%'
            try {
                queryWrapper.eq("id", Integer.valueOf(key));
            } catch (Exception ignored) {
                // 啥也不干
            }
            queryWrapper.or().like("name", key)
                .or().like("address", key).or().like("areacode", key);
        }
        IPage<WareInfoEntity> page = this.page(
            new Query<WareInfoEntity>().getPage(params),
            queryWrapper
        );

        return new PageUtils(page);
    }

    /**
     * 通过用户地址查询运费信息
     * @param addrId 地址id
     * @return BigDecimal
     * @author Clover You
     * @date 2022/2/26 3:07 下午
     */
    @Override
    public FareVo getFare(Long addrId) {
        FareVo fareVo = new FareVo();
        try {
            R info = memberFeignService.getAddrInfo(addrId);
            if (info.getCode() != 0) {
                return null;
            }
            MemberAddressTo data = info.getData("memberReceiveAddress", new TypeReference<MemberAddressTo>() {
            });
            String phone = data.getPhone();
            String substring = phone.substring(phone.length() - 1);
            fareVo.setAddress(data);
            fareVo.setFare(new BigDecimal(substring));
        } catch (Exception e) {
            fareVo.setFare(new BigDecimal("0.0"));
        }
        return fareVo;
    }
}
