package top.ctong.gulimall.product.dao;

import org.apache.ibatis.annotations.Param;
import top.ctong.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.ctong.gulimall.product.vo.SkuItemVo;

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
 * sku销售属性&值
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    /**
     * 通过spu id查询当前spu所有销售属性
     *
     * @param spuId spu id
     * @return List<ItemSaleAttrsVo>
     * @author Clover You
     * @date
     */
    List<SkuItemVo.ItemSaleAttrsVo> getSaleAttrsBySpuId(@Param("spuId") Long spuId);

    /**
     * 通过指定sku id 查询属性信息
     * @param skuId
     * @return List<String>
     * @author Clover You
     * @date 2022/2/18 6:59 下午
     */
    List<String> getSkuSaleAttrValuesAsStringList(@Param("skuId") Long skuId);
}
