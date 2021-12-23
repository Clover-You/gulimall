package top.ctong.gulimall.search.test;

//import lombok.extern.slf4j.Slf4j;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.ctong.gulimall.search.config.GuliMallElasticSearchConfig;

import java.io.IOException;

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
 * ES 测试
 * </p>
 * @author Clover You
 * @create 2021-12-21 08:36
 */
@Slf4j
@SpringBootTest
class ElasticSearchTest {

    @Autowired
    private RestHighLevelClient esClient;

    @Test
    @DisplayName("测试 ES 是否加载成功")
    void testLoad() {
        assert esClient != null;
    }

    @Test
    @DisplayName("存储数据")
    void indexTest() throws IOException {
        IndexRequest request = new IndexRequest("users");
        request.id("1");
        User user = new User();
        user.setUserName("clover");
        user.setAge(19);
        request.source(new Gson().toJson(user), XContentType.JSON);
        IndexResponse index = esClient.index(request, GuliMallElasticSearchConfig.COMMON_OPTIONS);
        log.info("index: {}", index);
    }

    @Test
    @DisplayName("复杂检索")
    void searchRequestTest() throws IOException {
        // 创建一个检索请求
        SearchRequest request = new SearchRequest();
        // 指定索引
        request.indices("bank");
        // 创建检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(100);
        // "query": {
        //     "match_all": {}
        // }
         searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        // "query": {
        //     "match": {"address", "mill"}
        // }
//        searchSourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));
        // 聚合
        //"aggs": {
        //  "ageAgg": {
        //    "terms": {
        //      "field": "age",
        //      "size": 100
        //    }
        //  }
        //}
        searchSourceBuilder.aggregation(AggregationBuilders.terms("ageAgg").field("age").size(100)
                //"aggs": {
                //  "ageAgg": {
                //    "terms": {
                //      "field": "age",
                //      "size": 100
                //    },
                //    "aggs": {
                //      "ageAvg": {
                //        "avg": {
                //          "field": "balance"
                //        }
                //      }
                //    }
                //  }
                //}
                .subAggregation(AggregationBuilders.avg("balanceAvg").field("balance"))
        );
        // 指定DSL
        request.source(searchSourceBuilder);
        // 执行检索
        SearchResponse search = esClient.search(request, GuliMallElasticSearchConfig.COMMON_OPTIONS);
    }

    @Data
    class User {
        private String userName;

        private Integer age;
    }

}


