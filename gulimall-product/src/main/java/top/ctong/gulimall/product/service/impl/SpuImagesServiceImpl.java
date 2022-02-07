package top.ctong.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.common.utils.Query;

import top.ctong.gulimall.product.dao.SpuImagesDao;
import top.ctong.gulimall.product.entity.SkuImagesEntity;
import top.ctong.gulimall.product.entity.SpuImagesEntity;
import top.ctong.gulimall.product.service.SpuImagesService;


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
 * spu图片
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 保存spu图片
     * @param spuId spuId
     * @param images 图片路径
     * @author Clover You
     * @date 2021/12/9 10:54
     */
    @Override
    public void saveImagesByUrl(Long spuId, List<String> images) {
        if (images == null || images.isEmpty()) {
            return;
        }
        List<SpuImagesEntity> imagesEntityList = images.stream().map(image -> {
            SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
            spuImagesEntity.setImgUrl(image);
            spuImagesEntity.setSpuId(spuId);
            return spuImagesEntity;
        }).collect(Collectors.toList());
        this.saveBatch(imagesEntityList);
    }

    /**
     * 通过skuId查询spu图片
     *
     * @param skuId sku id
     * @return List<SpuImagesEntity>
     * @author Clover You
     * @date
     */
    @Override
    public List<SpuImagesEntity> getImagesBySkuId(Long skuId) {
//        baseMapper.selectList(new QueryWrapper<SkuImagesEntity>().eq("sku_id"));
        return null;
    }


}