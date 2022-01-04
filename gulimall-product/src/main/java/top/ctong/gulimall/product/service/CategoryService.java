package top.ctong.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.product.entity.CategoryEntity;
import top.ctong.gulimall.product.vo.Catalog2Vo;

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
 * 商品三级分类
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-15 09:51:26
 */
public interface CategoryService extends IService<CategoryEntity> {

    /**
     * 查询叶
     * @param params 自定义查询条件
     * @return PageUtils
     * @author Clover You
     * @date 2021/11/21 20:36
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询所有分类并构造树形结构
     * @return List<CategoryEntity>
     * @author Clover You
     * @date 2021/11/21 20:36
     */
    List<CategoryEntity> listWithTree();

    /**
     * 通过菜单id删除菜单
     * @param asList id列表
     * @author Clover You
     * @date 2021/11/22 14:48
     */
    void removeMenuByIds(List<Long> asList);

    /**
     * 通过分组id查找分组路径
     * @param catelogId 分组id
     * @return Long
     * @author Clover You
     * @date 2021/11/27 08:52
     */
    Long[] findCategoryPath(Long catelogId);

    /**
     * 集联更新分类
     * @param category 分类信息
     * @author Clover You
     * @date 2021/11/27 11:00
     */
    void updateCascade(CategoryEntity category);

    /**
     * 查询一级分类
     * @return List<CategoryEntity>
     * @author Clover You
     * @date 2021/12/26 10:41
     */
    List<CategoryEntity> getLeve1Category();

    /**
     * 查出所有分类，以{"1": {Catalog2Vo}} 的形式返回
     * @return Map<String,Object>
     * @author Clover You
     * @date 2021/12/26 14:50
     */
    Map<String, List<Catalog2Vo>> getCatalogJson() throws InterruptedException;
}

