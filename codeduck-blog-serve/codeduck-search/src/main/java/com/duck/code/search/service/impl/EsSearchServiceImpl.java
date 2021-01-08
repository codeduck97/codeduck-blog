package com.duck.code.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.duck.code.commons.entity.search.EsBlogIndex;
import com.duck.code.search.config.ElasticConfig;
import com.duck.code.search.constant.EsConstant;
import com.duck.code.commons.entity.search.SearchBlogResult;
import com.duck.code.commons.entity.search.SearchParam;
import com.duck.code.search.service.EsSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-08 13:35
 */
@Service
@Slf4j
public class EsSearchServiceImpl implements EsSearchService {

    @Resource
    private RestHighLevelClient client;

    @Override
    public SearchBlogResult searchBlogByKeyword(SearchParam searchParam) {
        SearchBlogResult searchBlogResult = null;
        SearchRequest searchRequest = buildSearchRequestByKeyword(searchParam);
        try {
            SearchResponse searchResponse = client.search(searchRequest, ElasticConfig.COMMON_OPTIONS);
            searchBlogResult = buildSearchResult(searchParam,searchResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchBlogResult;
    }

    @Override
    public SearchBlogResult searchAllBlogs(SearchParam searchParam) {
        SearchBlogResult searchBlogResult = null;
        SearchRequest searchRequest = buildSearchAllBlogsRequest(searchParam);
        try {
            SearchResponse searchResponse = client.search(searchRequest, ElasticConfig.COMMON_OPTIONS);
            searchBlogResult = buildSearchResult(searchParam, searchResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchBlogResult;
    }

    private SearchRequest buildSearchAllBlogsRequest(SearchParam searchParam) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //1. 构建bool query
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.filter(QueryBuilders.termQuery("status", 1));
        // bool query 构建完毕
        searchSourceBuilder.query(boolQueryBuilder);
        // 2. 分页
        searchSourceBuilder.from((searchParam.getPageNum() - 1) * EsConstant.BLOG_PAGESIZE);
        searchSourceBuilder.size(EsConstant.BLOG_PAGESIZE);
        log.debug("Query DSL语句 {}",searchSourceBuilder.toString());
        SearchRequest request = new SearchRequest(new String[]{EsConstant.BLOG_INDEX}, searchSourceBuilder);
        return request;
    }

    /**
     * 构建 Query DSL 查询语句
     */
    private SearchRequest buildSearchRequestByKeyword(SearchParam searchParam) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //1. 构建bool query
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        if (!StringUtils.isEmpty(searchParam.getKeyword())) {
            // 1.1 bool must
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchParam.getKeyword(), "title","author","content"));

            // 1.2 bool filter
            boolQueryBuilder.filter(QueryBuilders.termQuery("status", 1));
        }
        // bool query 构建完毕
        searchSourceBuilder.query(boolQueryBuilder);

        // 2. 分页
        searchSourceBuilder.from((searchParam.getPageNum() - 1) * EsConstant.BLOG_PAGESIZE);
        searchSourceBuilder.size(EsConstant.BLOG_PAGESIZE);

        // 3. 高亮
        if (!StringUtils.isEmpty(searchParam.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("title");
            highlightBuilder.field("author");
            highlightBuilder.field("content");
            highlightBuilder.requireFieldMatch(false);
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            // 高亮字段构建完毕
            searchSourceBuilder.highlighter(highlightBuilder);
        }

        log.debug("Query DSL语句 {}",searchSourceBuilder.toString());
        SearchRequest request = new SearchRequest(new String[]{EsConstant.BLOG_INDEX}, searchSourceBuilder);
        return request;
    }

    /**
     * 封装查询结果
     */
    private SearchBlogResult buildSearchResult(SearchParam searchParam, SearchResponse searchResponse) {

        SearchBlogResult searchBlogResult = new SearchBlogResult();

        SearchHits hits = searchResponse.getHits();

        if (hits.getHits() != null && hits.getTotalHits().value > 0) {
            List<EsBlogIndex> blogIndexList = new ArrayList<>();
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                EsBlogIndex esBlogIndex = JSON.parseObject(sourceAsString, EsBlogIndex.class);

                // 设置高亮属性
                if (!StringUtils.isEmpty(searchParam.getKeyword())) {
                    HighlightField title = hit.getHighlightFields().get("title");
                    if (title != null) esBlogIndex.setTitle(title.getFragments()[0].string());
                    HighlightField author = hit.getHighlightFields().get("author");
                    if (author != null) esBlogIndex.setTitle(author.getFragments()[0].string());
                    HighlightField content = hit.getHighlightFields().get("content");
                    if (content != null) esBlogIndex.setTitle(content.getFragments()[0].string());
                }
                blogIndexList.add(esBlogIndex);
            }
            searchBlogResult.setBlogIndexList(blogIndexList);
        }

        //2. 封装分页信息
        searchBlogResult.setPageNum(searchParam.getPageNum());
        long total = hits.getTotalHits().value;
        searchBlogResult.setTotal(total);
        Integer totalPages = (int)total % EsConstant.BLOG_PAGESIZE== 0 ?
                (int)total / EsConstant.BLOG_PAGESIZE : (int)total / EsConstant.BLOG_PAGESIZE + 1;
        searchBlogResult.setTotalPages(totalPages);
        return searchBlogResult;
    }

}
