package top.ctong.gulimall.ware.dao;

import org.apache.ibatis.annotations.Param;
import top.ctong.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
 * Copyright 2021 Clover You.
 * <p>
 * 商品库存
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-16 16:12:36
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    /**
     * 添加库存
     * @param skuId sku id
     * @param wareId 仓库id
     * @param skuNum 入库数量
     * @author Clover You
     * @date 2021/12/13 16:07
     */
    void addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

    /**
     * 通过skuId查询总库存量
     * stock - stock_locked
     * @param id
     * @return long
     * @author Clover You
     * @date 2021/12/22 10:55
     */
    Long getSkuStock(@Param("id") Long id);

    /**
     * 查询该商品在哪个仓库有库存
     * @param skuId 商品id
     * @return List<Long>
     * @author Clover You
     * @date 2022/3/1 8:10 上午
     */
    List<Long> listWareIdHasSkuStock(@Param("skuId") Long skuId);

    /**
     * 锁定指定库存
     * @param skuId 商品id
     * @param wareId 库存id
     * @param lockCount 锁定数量
     * @return Long
     * @author Clover You
     * @date 2022/3/1 8:47 上午
     */
    Long lockSkuStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("lockCount") Integer lockCount);

    /**
     * 解锁库存
     * @param skuId 商品id
     * @param wareId 库存id
     * @param skuNum 解锁数量
     * @author Clover You
     * @date 2022/3/7 7:34 下午
     */
    void unLockStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);
}
