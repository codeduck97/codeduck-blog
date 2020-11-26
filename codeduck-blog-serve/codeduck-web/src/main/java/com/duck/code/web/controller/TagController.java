package com.duck.code.web.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.entity.pojo.BlogArticle;
import com.duck.code.commons.entity.pojo.BlogSort;
import com.duck.code.commons.entity.pojo.BlogTag;
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
import java.util.List;

/**
 * @program: codeduck-blog-serve
 * @description: 标签控制器
 * @author: Code Duck
 * @create: 2020-11-22 15:24
 */
@RestController
@RequestMapping("/api/tag")
@Validated
@Api(value = "标签页面 API",tags = "标签页面 API")
@Slf4j
public class TagController {

    @Resource
    private BlogTagService blogTagService;

    @ApiOperation(value = "根据标签id获取博文列表")
    @GetMapping("/articles")
    public R getArticlesBySortId(@RequestParam("id") String id) {
        List<BlogArticle> articles = blogTagService.getArticlesByTagId(id);
        return R.ok(articles).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "获取标签列表",notes = "该集合列表只包含存在博文信息的标签")
    @GetMapping("list")
    public R getSortList() {
        List<BlogTag> tags = blogTagService.getTagList();
        return R.ok(tags).setCode(ResCode.OPERATION_SUCCESS);
    }
}
