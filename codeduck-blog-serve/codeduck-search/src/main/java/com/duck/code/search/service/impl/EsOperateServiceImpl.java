package com.duck.code.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.duck.code.commons.entity.search.EsBlogIndex;
import com.duck.code.search.config.ElasticConfig;
import com.duck.code.search.constant.EsConstant;
import com.duck.code.search.service.EsOperateService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-07 17:38
 */
@Service
@Slf4j
public class EsOperateServiceImpl implements EsOperateService {

    @Resource
    private RestHighLevelClient client;

    @Override
    public boolean saveOrUpdateBlogToBlogIndex01(EsBlogIndex esBlogIndex) throws IOException {
        IndexRequest indexRequest = new IndexRequest(EsConstant.BLOG_INDEX01);
        indexRequest.id(esBlogIndex.getBlogId());
        String blog = JSON.toJSONString(esBlogIndex);
        indexRequest.source(blog, XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, ElasticConfig.COMMON_OPTIONS);
        if (indexResponse.getResult()== DocWriteResponse.Result.CREATED) {
            log.info("博文已保存至Elasticsearch{{}}",indexResponse);
            return true;
        } else if (indexResponse.getResult()== DocWriteResponse.Result.UPDATED) {
            log.info("Elasticsearch中博文已被更新{{}}",indexResponse);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBlogFromBlogIndex01(EsBlogIndex esBlogIndex) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(EsConstant.BLOG_INDEX01, esBlogIndex.getBlogId());
        DeleteResponse deleteResponse = client.delete(deleteRequest, ElasticConfig.COMMON_OPTIONS);
        if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
            log.info("博文已从Elasticsearch删除", deleteResponse);
            return true;
        }
        return false;
    }
}
