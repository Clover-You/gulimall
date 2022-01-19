package top.ctong.gulimall.search.service.impl;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import top.ctong.gulimall.search.config.GuliMallElasticSearchConfig;
import top.ctong.gulimall.search.constant.EsConstant;
import top.ctong.gulimall.search.service.MallSearchService;
import top.ctong.gulimall.search.vo.SearchParam;
import top.ctong.gulimall.search.vo.SearchResult;

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
 * Copyright 2022 Clover You.
 * <p>
 * 商城检索服务实现
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-01-17 00:34
 */
@Slf4j
@Service("mallSearchService")
public class MallSearchServiceImpl implements MallSearchService {

    @Autowired
    private RestHighLevelClient esClient;

    /**
     * 通过检索条件查询所需的检索结果
     *
     * @param param 检索所有条件
     * @return SearchResult 检索结果
     * @author Clover You
     * @date 2022/1/17 00:35
     */
    @Override
    public SearchResult search(SearchParam param) {
        SearchResult result = null;
        // 准备检索请求
        SearchRequest searchRequest = buildSearchRequest(param);
        try {
            // 执行检索请求
            SearchResponse search = esClient.search(searchRequest, GuliMallElasticSearchConfig.COMMON_OPTIONS);
            result = buildSearchResult(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 构建检索结果
     *
     * @param search 检索结果
     * @return SearchResult
     * @author Clover You
     * @date 2022/1/17 21:39
     */
    private SearchResult buildSearchResult(SearchResponse search) {
        return null;
    }

    /**
     * 创建检索请求
     *
     * @param param 检索参数
     * @return SearchRequest
     * @author Clover You
     * @date 2022/1/17 21:36
     */
    private SearchRequest buildSearchRequest(SearchParam param) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 模匹配，过滤(按照属性，分类，品牌，价格区间，库存)
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (StringUtils.hasText(param.getKeyword())) {
            boolQuery.must(
                QueryBuilders.matchQuery("skuTitle", param.getKeyword())
            );
        }
        List<QueryBuilder> filter = boolQuery.filter();
        if (param.getCatalog3Id() != null) {
            filter.add(
                QueryBuilders.termQuery("catalogId", param.getCatalog3Id())
            );
        }
        if (param.getBrandId() != null && !param.getBrandId().isEmpty()) {
            filter.add(
                QueryBuilders.termsQuery("brandId", param.getBrandId())
            );
        }
        if (param.getAttrs() != null && !param.getAttrs().isEmpty()) {
            List<String> attrs = param.getAttrs();
            // [1_5寸:6.5寸, 2_16G:8G]
            attrs.forEach(attr -> {
                BoolQueryBuilder attrBoolQuery = QueryBuilders.boolQuery();

                // 1_5寸:6.5寸
                String[] s = attr.split("_");
                // 属性id
                String attrId = s[0];
                // 属性值
                String[] attrValues = s[1].split(":");
                attrBoolQuery.must(
                    QueryBuilders.termQuery("attrs.attrId", attrId)
                );
                attrBoolQuery.must(
                    QueryBuilders.termsQuery("attrs.attrValue", attrValues)
                );
                // 每次都需要生成一个新的nested
                filter.add(
                    QueryBuilders.nestedQuery("attrs", attrBoolQuery, ScoreMode.None)
                );
            });
        }
        filter.add(
            QueryBuilders.termQuery("hasStock", Integer.valueOf(1).equals(param.getHasStock()))
        );
        if (StringUtils.hasText(param.getSkuPrice())) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");
            // 1_500/_500/500_
            String[] prices = param.getSkuPrice().split("_");
            if (prices.length == 2) {
                rangeQuery.gte(prices[0]).lte(prices[1]);
            } else if (param.getSkuPrice().startsWith("_")) {
                rangeQuery.lte(prices[1]);
            } else if (param.getSkuPrice().endsWith("_")) {
                rangeQuery.gte(prices[0]);
            }
            filter.add(rangeQuery);
        }

        builder.query(boolQuery);

        //排序，分页，高亮，
        String sort = param.getSort();
        if (StringUtils.hasText(sort)) {
            String[] rules = sort.split("_");
            SortOrder sortOrder = rules[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            builder.sort(rules[0], sortOrder);
        }

        builder.from(Math.max((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGE_SIZE, 0));
        builder.size(EsConstant.PRODUCT_PAGE_SIZE);
        if (StringUtils.hasText(param.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color: #900;'>");
            highlightBuilder.postTags("</b>");
            builder.highlighter(highlightBuilder);
        }
        //聚合分析

        // region 品牌聚合
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg");
        brandAgg.field("brandId");
        brandAgg.size(50);
        // 品牌名称聚合
        TermsAggregationBuilder brandNameAgg = AggregationBuilders.terms("brand_name_agg");
        brandNameAgg.field("brandName");
        brandNameAgg.size(1);
        brandAgg.subAggregation(brandNameAgg);
        // 品牌logo聚合
        TermsAggregationBuilder brandImgAgg = AggregationBuilders.terms("brand_img_agg");
        brandImgAgg.field("brandImg");
        brandImgAgg.size(1);
        brandAgg.subAggregation(brandImgAgg);
        builder.aggregation(brandAgg);

        // endregion

        // region 分类聚合
        TermsAggregationBuilder catalogAgg = AggregationBuilders.terms("catalog_agg");
        catalogAgg.field("catalogId");
        catalogAgg.size(20);
        // 分类名称
        TermsAggregationBuilder catalogNameAgg = AggregationBuilders.terms("catalog_name_agg");
        catalogNameAgg.field("catalogName");
        catalogNameAgg.size(1);
        catalogAgg.subAggregation(catalogNameAgg);

        builder.aggregation(catalogAgg);
        // endregion

        // region 属性聚合
        NestedAggregationBuilder attrAgg = AggregationBuilders.nested("attr_agg", "attrs");
        TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attr_id_agg");
        attrIdAgg.field("attrs.attrId");
        TermsAggregationBuilder attrNameAgg = AggregationBuilders.terms("attr_name_agg");
        attrNameAgg.field("attrs.attrName");
        attrNameAgg.size(1);
        attrIdAgg.subAggregation(attrNameAgg);

        // 属性值
        TermsAggregationBuilder attrValueAgg = AggregationBuilders.terms("attr_value_agg");
        attrValueAgg.field("attrs.attrValue");
        attrValueAgg.size(30);
        attrIdAgg.subAggregation(attrValueAgg);
        attrAgg.subAggregation(attrIdAgg);

        builder.aggregation(attrAgg);
        // endregion
        log.info("构建的DSL：{}", builder.toString());

        return new SearchRequest(new String[]{EsConstant.INDEX.PRODUCT}, builder);
    }

}
