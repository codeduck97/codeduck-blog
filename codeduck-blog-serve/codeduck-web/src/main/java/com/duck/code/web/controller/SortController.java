package com.duck.code.web.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.entity.blog.BlogArticle;
import com.duck.code.commons.entity.blog.BlogSort;
import com.duck.code.web.service.BlogSortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: codeduck-blog-serve
 * @description: 分类信息控制器
 * @author: Code Duck
 * @create: 2020-11-22 15:24
 */
@RestController
@RequestMapping("/api/sort")
@Validated
@Api(value = "分类页面 API", tags ="分类页面 API")
@Slf4j
public class SortController {

    @Resource
    private BlogSortService blogSortService;

    @ApiOperation(value = "根据分类id获取博文列表")
    @GetMapping("/articles")
    public R getArticlesBySortId(@RequestParam("id") String id) {
        List<BlogArticle> articles = blogSortService.getArticlesBySortId(id);
        return R.ok(articles).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "获取分类列表",notes = "该集合列表只包含存在博文信息的标签")
    @GetMapping("list")
    public R getSortList() {
        List<BlogSort> sorts = blogSortService.getSortList();
        return R.ok(sorts).setCode(ResCode.OPERATION_SUCCESS);
    }

}
