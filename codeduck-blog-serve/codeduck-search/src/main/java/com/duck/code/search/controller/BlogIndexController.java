package com.duck.code.search.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.entity.search.EsBlogIndex;
import com.duck.code.commons.utils.CommonRes;
import com.duck.code.commons.entity.search.SearchBlogResult;
import com.duck.code.commons.entity.search.SearchParam;
import com.duck.code.search.service.EsOperateService;
import com.duck.code.search.service.EsSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-07 19:13
 */
@Slf4j
@RestController
@RequestMapping("/api/es/blog")
public class BlogIndexController {

    @Resource
    private EsOperateService esOperateService;


    @Resource
    private EsSearchService esSearchService;

    @PostMapping("/search")
    public R searchBlog(@RequestBody SearchParam searchParam) {
        SearchBlogResult searchBlogResult = esSearchService.searchBlogByKeyword(searchParam);
        return CommonRes.success(searchBlogResult);
    }

    @PostMapping("/all")
    public R searchAllBlogs(@RequestBody SearchParam searchParam) {
        SearchBlogResult searchBlogResult = esSearchService.searchAllBlogs(searchParam);
        return CommonRes.success(searchBlogResult);
    }

    @PostMapping("/save")
    public R saveOrUpdateBlogToEs(@RequestBody EsBlogIndex esBlogIndex) {
        try {
            esOperateService.saveOrUpdateBlogToBlogIndex01(esBlogIndex);
            return CommonRes.success(null);
        } catch (IOException e) {
            log.error("博文保存至Elasticsearch错误{{}}", e.getMessage());
            return CommonRes.fail("博文保存至Elasticsearch失败");
        }
    }

    @DeleteMapping
    public R deleteBlogFromEs(@RequestBody EsBlogIndex esBlogIndex){
        try {
            esOperateService.deleteBlogFromBlogIndex01(esBlogIndex);
            return CommonRes.success(null);
        } catch (IOException e) {
            log.error("博文从Elasticsearch删除失败{{}}",e.getMessage());
            return CommonRes.fail("博文从Elasticsearch删除失败{{}}");
        }

    }
}
