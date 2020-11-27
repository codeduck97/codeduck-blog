package com.duck.code.web.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.entity.blog.BlogArticle;
import com.duck.code.commons.entity.blog.BlogSort;
import com.duck.code.commons.entity.blog.BlogTag;
import com.duck.code.web.service.BlogArticleService;
import com.duck.code.web.service.BlogSortService;
import com.duck.code.web.service.BlogTagService;
import com.duck.code.web.vo.BlogArticleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: codeduck-blog-serve
 * @description: 门户首页控制器
 * @author: Code Duck
 * @create: 2020-11-21 09:31
 */
@RestController
@RequestMapping("/api/home")
@Validated
@Api(value = "首页相关接口", tags = {"首页相关 API"})
@Slf4j
public class HomeController {

    @Resource
    private BlogArticleService blogArticleService;

    @Resource
    private BlogTagService blogTagService;

    @Resource
    private BlogSortService blogSortService;

    @ApiOperation(value = "获取博文列表", notes = "请注意页面大小和当前页码大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码,默认值：1", paramType = "query", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页面大小,默认值：5", paramType = "query", defaultValue = "5", required = true)
    })
    @GetMapping("/list")
    public R getAllBlogs(@Min(value = 1, message = "当前页 pageNum >= 1") @RequestParam(value = "pageNum", defaultValue = "1") long pageNum,
                         @Min(value = 1, message = "页面大小 pageSize >= 1") @RequestParam(value = "pageSize", defaultValue = "5") long pageSize) {
        List<BlogArticleVO> blogArticleList = blogArticleService.getAllBlogs(pageNum, pageSize);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("blogs", blogArticleList);
        resMap.put("total", blogArticleService.count());
        log.info("博文列表被请求，pageNum：{{}}，pageSize：{{}}", pageNum, pageSize);
        return R.ok(resMap).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "获取标签云信息",notes = "只返回存在文章的标签")
    @GetMapping("/tags")
    public R getTagCloud(){
        List<BlogTag> tags = blogTagService.getTagCloud();
        return R.ok(tags).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "获取分类列表信息",notes = "只返回存在文章的分类")
    @GetMapping("/sorts")
    public R getSortList(){
        List<BlogSort> sorts = blogSortService.getSortList();
        return R.ok(sorts).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "获取热门文章",notes = "只返回五条信息")
    @GetMapping("/hot")
    public R getHotArticles(){
        List<BlogArticle> articles = blogArticleService.getHotArticles();
        return R.ok(articles).setCode(ResCode.OPERATION_SUCCESS);
     }
}
