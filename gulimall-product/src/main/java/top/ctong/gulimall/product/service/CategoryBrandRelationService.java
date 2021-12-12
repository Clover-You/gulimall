package top.ctong.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.product.entity.BrandEntity;
import top.ctong.gulimall.product.entity.CategoryBrandRelationEntity;

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
 * 品牌分类关联
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-27 10:09:07
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    /** 
     * 分页查询关联详细信息
     * @param params 自定义查询规则
     * @return PageUtils
     * @author Clover You
     * @date 2021/11/27 10:24
     */
    PageUtils queryPage(Map<String, Object> params);

    /** 
     * 保存关联详细信息
     * @param categoryBrandRelation 关联信息
     * @author Clover You
     * @date 2021/11/27 10:23
     */
    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    /** 
     * 更新品牌名称
     * @param brandId 品牌id
     * @param name 品牌名称
     * @author Clover You
     * @date 2021/11/27 10:48
     */
    void updateBrand(Long brandId, String name);

    /**
     * 更新分类名称
     * @param catId 分类id
     * @param name 分类名称
     * @author Clover You
     * @date 2021/11/27 11:04
     */
    void updateCategory(Long catId, String name);

    /** 
     * 获取分类关联的品牌
     * @param catId 分类id
     * @return List<BrandEntity>
     * @author Clover You
     * @date 2021/11/29 10:01
     */
    List<BrandEntity> getBrandsByCatId(Long catId);
}

