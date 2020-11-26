package com.duck.code.web.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.entity.pojo.BlogArticle;
import com.duck.code.web.service.BlogArticleService;
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
 * @description: 博文归档控制器
 * @author: Code Duck
 * @create: 2020-11-24 19:47
 */
@RestController
@Validated
@Slf4j
@Api(value = "归档页面接口", tags = {"归档页面 API"})
@RequestMapping("/api/archive")
public class ArchiveController {

    @Resource
    private BlogArticleService blogArticleService;

    @ApiOperation(value = "获取博文的创建年月时间",notes = "返回的时间以年月为单位")
    @GetMapping("/date")
    public R getArticleCreationTime() {
        List<String> time = blogArticleService.getArticleCreationTime();
        return R.ok(time).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "根据年月时间获取博文集合")
    @GetMapping("/articles")
    public R getAllArticlesByYearMonth(@RequestParam("date") String date) {
        List<BlogArticle> articles = blogArticleService.getAllArticlesByYearMonth(date);
        return R.ok(articles).setCode(ResCode.OPERATION_SUCCESS);
    }

}
