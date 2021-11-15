package top.ctong.gulimall.product.dao;

import top.ctong.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author Clover You
 * @email 2621869236@qq.com
 * @date 2021-11-15 09:33:32
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
