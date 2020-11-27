package com.duck.code.web.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.entity.blog.BlogArticle;
import com.duck.code.web.service.BlogArticleService;
import com.duck.code.web.service.BlogSortService;
import com.duck.code.web.service.BlogTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @program: codeduck-blog-serve
 * @description: 博文信息控制器
 * @author: Code Duck
 * @create: 2020-11-22 14:30
 */
@RestController
@Validated
@Slf4j
@Api(value = "博文详情页面相关接口", tags = {"博文详情页面 API"})
@RequestMapping("/api/article")
public class ArticleController {
    @Resource
    private BlogArticleService blogArticleService;

    @Resource
    private BlogTagService blogTagService;

    @Resource
    private BlogSortService blogSortService;

    @ApiOperation(value = "根据Id获取博文信息")
    @GetMapping("/info")
    public R getArticleInfo(@NotBlank @RequestParam("id") String id) {
        BlogArticle article = blogArticleService.getById(id);
        if (article != null) {
            return R.ok(article).setCode(ResCode.OPERATION_SUCCESS);
        } else {
            return R.failed("该博文不存在！").setCode(ResCode.OPERATION_FAIL);
        }
    }

    @ApiOperation(value = "获取分类字典信息")
    @GetMapping("/sort")
    public R getSortDic(){
        Map<String, String> sortDic = blogSortService.getSortDic();
        return R.ok(sortDic).setCode(ResCode.OPERATION_SUCCESS);
    }


    @ApiOperation(value = "获取标签字典信息")
    @GetMapping("/tag")
    public R getTagDic(){
        Map<String, String> tagDic = blogTagService.getTagDic();
        return R.ok(tagDic).setCode(ResCode.OPERATION_SUCCESS);
    }
}
