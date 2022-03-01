package top.ctong.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.ware.entity.WareSkuEntity;
import top.ctong.gulimall.ware.exception.NoStockException;
import top.ctong.gulimall.ware.vo.LockStockResultVo;
import top.ctong.gulimall.ware.vo.SkuHasStockVo;
import top.ctong.gulimall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;


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
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 入库
     * @param skuId sku id
     * @param wareId 仓库id
     * @param skuNum 添加的数量
     * @author Clover You
     * @date 2021/12/13 16:05
     */
    void addStock(Long skuId, Long wareId, Integer skuNum);

    /**
     * 检查sku是否有库存
     * @param skuIds sku id列表
     * @return List<SkuHasStockVo>
     * @author Clover You
     * @date 2021/12/22 10:45
     */
    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    /**
     * 根据指定信息锁定库存
     * @param vo 商品锁定信息
     * @return Boolean
     * @author Clover You
     * @date 2022/2/27 8:35 下午
     */
    Boolean orderLockStock(WareSkuLockVo vo) throws NoStockException;

    /**
     * 查询该商品在哪有库存
     * @param skuId 商品id
     * @return List<Long>
     * @author Clover You
     * @date 2022/3/1 8:09 上午
     */
    List<Long> listWareIdHasSkuStock(Long skuId);
}

