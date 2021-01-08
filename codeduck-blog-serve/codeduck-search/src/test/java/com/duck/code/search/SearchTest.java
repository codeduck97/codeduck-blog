package com.duck.code.search;

import com.alibaba.fastjson.JSON;
import com.duck.code.commons.entity.search.EsBlogIndex;
import com.duck.code.search.config.ElasticConfig;
import com.duck.code.commons.entity.search.SearchBlogResult;
import com.duck.code.commons.entity.search.SearchParam;
import com.duck.code.search.service.EsOperateService;
import com.duck.code.search.service.EsSearchService;
import lombok.Data;
import lombok.ToString;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-06 18:42
 */
@SpringBootTest
public class SearchTest {

    @Resource
    private RestHighLevelClient client;

    @Resource
    private EsOperateService esOperateService;

    @Resource
    private EsSearchService esSearchService;

    @Test
    public void testSearch(){
        SearchParam searchParam = new SearchParam();
        searchParam.setKeyword("Elasticsearch");
        SearchBlogResult searchBlogResult = esSearchService.searchBlogByKeyword(searchParam);
        System.out.println(searchBlogResult);
    }

    @Test
    void testAddData() throws IOException {
        IndexRequest indexRequest = new IndexRequest("test_idx01");
        User user = new User();
        user.setAge(21);
        user.setName("大房子");
        String s = JSON.toJSONString(user);
        indexRequest.source(s, XContentType.JSON);
        IndexResponse response = client.index(indexRequest, ElasticConfig.COMMON_OPTIONS);
        System.out.println(response.getResult());
        DocWriteResponse.Result result = response.getResult();
        String lowercase = result.getLowercase();
        System.out.println(lowercase.equals("created"));
    }

    @Test
    void testSaveBlogToEs() throws IOException {
        EsBlogIndex blog = new EsBlogIndex();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("houduan");
        tags.add("qiandaun");
        blog.setAuthor("codeduck");
        blog.setBlogId("3");
        blog.setContent("在线,JSON,JSON 校验,格式化,xml转json 工具,在线工具,json视图,可视化,程序,服务器,域名注册,正则表达式,测试,在线json格式化工具,json 格式化,json格式化工具,json...");
        blog.setCoverImage("http://localhost:8080/image");
        blog.setCreateTime(LocalDateTime.now());
        blog.setHits(123);
        blog.setSort("SQL");
        blog.setTags(tags);
        blog.setTitle("后端大学习");
        blog.setUserId("iiidjd");
        boolean b = esOperateService.saveOrUpdateBlogToBlogIndex01(blog);
        System.out.println(b);
    }

    @Test
    public void testString(){
        EsBlogIndex index = new EsBlogIndex();
        index.setUserId("123");
        String s2 = "123";
        System.out.println(index.getUserId().equals(s2));
    }

    @Test
    void testSearchData() throws IOException {
        SearchRequest searchRequest = new SearchRequest("test_idx01");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();



        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 将JSON数据转为Map对象
//        Map o = JSON.parseObject(searchResponse.toString(), Map.class);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit: hits1) {
            String s = hit.getSourceAsString();
            System.out.println(s);
            // 将
            User user = JSON.parseObject(s, User.class);
            System.out.println(user.toString());
        }

    }

    @Test
    public void testAgg() throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.query(QueryBuilders.matchAllQuery());

        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age");
        builder.aggregation(ageAgg);

        AvgAggregationBuilder ageAvg = AggregationBuilders.avg("ageAvg").field("age");
        builder.aggregation(ageAvg);

        SearchRequest request = new SearchRequest("test_idx01");
        request.source(builder);

        SearchResponse response = client.search(request, ElasticConfig.COMMON_OPTIONS);

        Aggregations aggregations = response.getAggregations();
        Terms ageAgg1 = aggregations.get("ageAgg");
        for (Terms.Bucket bucket : ageAgg1.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println(keyAsString + "------" + bucket.getDocCount());
        }

        Avg ageAvg1 = aggregations.get("ageAvg");
        System.out.println("ageAvg: " + ageAvg1.getValue());
    }

    @ToString
    @Data
    static class User{
        String name;
        Integer age;
    }
}
