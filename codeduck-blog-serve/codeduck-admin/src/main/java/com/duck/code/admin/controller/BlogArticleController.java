package com.duck.code.admin.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.admin.vo.BlogArticleVO;
import com.duck.code.admin.service.BlogArticleService;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.constant.ResMsg;
import com.duck.code.commons.entity.blog.BlogArticle;
import com.duck.code.commons.validator.Insert;
import com.duck.code.commons.validator.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-20
 */
@RestController
@Slf4j
@Validated
@RequestMapping("/api/blog")
@Api(value = "博文管理API", tags = {"博文管理 API"})
public class BlogArticleController {

    @Resource
    private BlogArticleService blogArticleService;

    @ApiOperation(value = "获取博文列表", notes = "请注意页面大小和当前页码大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码,默认值：1", paramType = "query", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页面大小,默认值：5", paramType = "query", defaultValue = "5", required = true)
    })
    @PostMapping("/list")
    public R getBlogList(
            @Min(value = 1, message = "当前页 pageNum >= 1") @RequestParam(value = "pageNum", defaultValue = "1") long pageNum,
            @Min(value = 1, message = "页面大小 pageSize >= 1") @RequestParam(value = "pageSize", defaultValue = "5") long pageSize) {
        List<BlogArticleVO> blogArticleList = blogArticleService.getBlogList(pageNum, pageSize);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("blogs", blogArticleList);
        resMap.put("total", blogArticleService.count());
        log.info("博文列表被请求，pageNum：{{}}，pageSize：{{}}", pageNum, pageSize);
        return R.ok(resMap).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "增加博文信息", notes = "请注意博文对象必填的属性")
    @PostMapping("/add")
    public R addBlog(@Validated({Insert.class}) @RequestBody BlogArticle blogArticle) {
        if (blogArticleService.saveOrUpdate(blogArticle)) {
            log.info("新增博客信息 {{}}", blogArticle);
            return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
        }
        return R.failed(ResMsg.OPERATION_FAIL).setCode(ResCode.OPERATION_FAIL);
    }

    @ApiOperation(value = "更新博文信息", notes = "请注意博文对象必填的属性")
    @PutMapping("/update")
    public R updateBlog(@Validated({Update.class}) @RequestBody BlogArticle blogArticle) {
        blogArticle.setUpdateTime(LocalDateTime.now());
        if (blogArticleService.updateById(blogArticle)) {
            log.info("博文信息已更新为{{}}", blogArticle);
            return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
        }
        return R.failed(ResMsg.OPERATION_FAIL).setCode(ResCode.OPERATION_FAIL);
    }


    @ApiOperation(value = "删除博文信息", notes = "根据id删除博文信息")
    @DeleteMapping("/delete")
    public R deleteBlog(@NotBlank(message = "博文id不能为空") @RequestParam("id") String id) {
        BlogArticle article = blogArticleService.getById(id);
        if (article != null) {
            if (blogArticleService.removeById(id)) {
                log.info("此博文已删除 {{}}", article);
                return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
            }
            return R.failed(ResMsg.OPERATION_FAIL).setCode(ResCode.OPERATION_FAIL);
        }
        return R.failed("该博文不存在").setCode(ResCode.OPERATION_REJECT);
    }

    @ApiOperation(value = "获取博文信息", notes = "根据博文名称获取博文信息")
    @ApiImplicitParam(name = "title", value = "博文名称", paramType = "query", required = true)
    @GetMapping("/get")
    public R getBlogArticleByName(@NotBlank(message = "博文名称不能为空") @RequestParam("title") String title) {
        List<BlogArticleVO> blogArticle = blogArticleService.getBlogByTitle(title);
        if (!blogArticle.isEmpty()) {
            log.info("请求获取博文信息 {{}}", blogArticle);
            return R.ok(blogArticle).setCode(ResCode.OPERATION_SUCCESS);
        }
        return R.failed("该博文不存在").setCode(ResCode.OPERATION_FAIL);
    }

}
