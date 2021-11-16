package top.ctong.gulimall.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ctong.gulimall.product.entity.BrandEntity;
import top.ctong.gulimall.product.service.BrandService;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Clover You");
        brandService.save(brandEntity);
    }

}
