package top.ctong.gulimall.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ctong.gulimall.product.entity.BrandEntity;
import top.ctong.gulimall.product.service.BrandService;
import top.ctong.gulimall.product.service.CategoryService;

@SpringBootTest
@Slf4j
class GulimallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Clover You");
        brandService.save(brandEntity);
    }

    @Test
    @DisplayName("查找所有父节点并生成节点map")
    void findCategoryPathTest() {
        Long[] categoryPath = categoryService.findCategoryPath(225L);
        log.info("map: {}", categoryPath);
    }

}
