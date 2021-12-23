package top.ctong.gulimall.search.service.impl;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ctong.gulimall.common.to.es.SkuEsModel;
import top.ctong.gulimall.search.config.GuliMallElasticSearchConfig;
import top.ctong.gulimall.search.constant.EsConstant;
import top.ctong.gulimall.search.service.ProductSaveService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
 * 商品保存服务实现
 * </p>
 * @author Clover You
 * @create 2021-12-22 15:08
 */
@Slf4j
@Service("productSaveService")
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private RestHighLevelClient esClient;

    /**
     * 上架指定商品
     * @param skuEsModelList 商品列表
     * @author Clover You
     * @date 2021/12/22 15:08
     */
    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModelList) throws IOException {
        // TODO 建立 product 索引与映射关系
//        IndexRequest _indexReqest  = new IndexRequest(EsConstant.INDEX.PRODUCT);
//        _indexReqest

        // 保存到索引
        BulkRequest bulkRequest = new BulkRequest(EsConstant.INDEX.PRODUCT);
        for (SkuEsModel skuEsModel : skuEsModelList) {
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.id(Long.toString(skuEsModel.getSkuId()));
            bulkRequest.add(indexRequest);
            indexRequest.source(new Gson().toJson(skuEsModel), XContentType.JSON);
        }
        BulkResponse bulk = esClient.bulk(bulkRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());

        log.error("商品上架错误: {}", collect);
        return !bulk.hasFailures();
    }
}
