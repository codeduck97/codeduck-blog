package com.duck.code.admin.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.admin.vo.BlogTagVO;
import com.duck.code.admin.service.BlogTagService;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.constant.ResMsg;
import com.duck.code.commons.entity.blog.BlogTag;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-30
 */
@RestController
@Validated
@Slf4j
@RequestMapping("/api/blog/tag")
@Api(value = "博文标签API", tags = {"标签 API"})
public class BlogTagController {

    @Resource
    private BlogTagService blogTagService;

    @ApiOperation(value = "获取博客标签列表", notes = "分页获取博客标签列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码，默认值：1，最小值：1", paramType = "query", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页面大小，默认值：5，最小值：1", paramType = "query", defaultValue = "5", required = true)
    })
    @PostMapping("/list")
    public R getBlogTagList(@Min(value = 1, message = "当前页 pageNum >= 1") @RequestParam(value = "pageNum", defaultValue = "1") long pageNum,
                            @Min(value = 1, message = "页面大小 pageSize >= 1") @RequestParam(value = "pageSize", defaultValue = "5") long pageSize) {
        List<BlogTagVO> blogTags = blogTagService.getBlogTagList(pageNum, pageSize);
        int total = blogTagService.count();
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("tags", blogTags);
        resMap.put("total", total);
        log.info("博客标签列表被请求，pageNum：{{}}，pageSize：{{}}",pageNum,pageSize);
        return R.ok(resMap).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "新增博客标签", notes = "注意tagIndex：排序权重，其数值不能小于0")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagName", value = "标签名称", paramType = "query", required = true),
            @ApiImplicitParam(name = "tagIndex", value = "标签排序权重，默认值：0，最小值：0", required = true,
                    paramType = "query", defaultValue = "0")
    })
    @PostMapping("/add")
    public R addBlogTag(@NotBlank(message = "标签名不能为空") @RequestParam("tagName") String name,
                        @Min(value = 0, message = "最小值为0") @RequestParam(value = "tagIndex", defaultValue = "0") Integer index) {

        // 1. 查询数据库查,是否存在此标签
        if (!blogTagService.existTagName(name)) {
            // 2. 新增标签
            BlogTag blogTag = new BlogTag();
            blogTag.setTagName(name);
            blogTag.setTagIndex(index);
            if (blogTagService.save(blogTag)) {
                log.warn("添加博客标签：{{}}",blogTagService.getBlogTagByName(name));
                return R.ok(null).setCode(ResCode.OPERATION_SUCCESS).setMsg(ResMsg.OPERATION_SUCCESS);
            }
            return R.failed(ResMsg.OPERATION_FAIL).setCode(ResCode.OPERATION_FAIL);
        }
        return R.ok(null).setCode(ResCode.OPERATION_REJECT).setMsg("标签名已存在");
    }

    @ApiOperation(value = "根据id删除博客标签")
    @DeleteMapping("/delete")
    public R deleteBlogTage(@NotBlank(message = "标签id不能为空") @RequestParam("id") String tagId) {
        // 1. 根据id查询此id是否存在文章
        if (!blogTagService.existBlogArticle(tagId)) {
            BlogTag tag = blogTagService.getById(tagId);
            // 2. 此标签下无文章存在，可进行删除
            if (blogTagService.removeById(tagId)) {
                log.warn("博客标签已被删除：{{}} ", tag);
                return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
            }
            return R.failed("该博客标签不存在").setCode(ResCode.OPERATION_FAIL);
        }
        return R.ok(null).setMsg(ResMsg.BLOG_UNDER_THIS_TAG).setCode(ResCode.OPERATION_REJECT);
    }

    @ApiOperation(value = "更新博客标签", notes = "更新时标签id，tagName,tagIndex不能为空")
    @PutMapping("/update")
    public R updateBlogTag(@NotBlank(groups = {Update.class}) @RequestBody BlogTagVO blogTagVO) {
        List<BlogTag> tags = blogTagService.getBlogTagByName(blogTagVO.getTagName());

        // 根据未出现同名标签
        if (tags.isEmpty()) {
            if (blogTagService.updateBlogTag(blogTagVO)) {
                log.info("博客标签：{{}} 已被更新",blogTagVO);
                return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
            }
            return R.failed(ResMsg.OPERATION_FAIL).setCode(ResCode.OPERATION_FAIL);
        } else {
            BlogTag blogTag = tags.get(0);
            if (blogTag.getId().equals(blogTagVO.getId())) {
                if (blogTagService.updateBlogTag(blogTagVO)) {
                    log.info("博客标签：{{}} 已被更新",blogTagVO);
                    return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
                }
                return R.failed(ResMsg.OPERATION_FAIL).setCode(ResCode.OPERATION_FAIL);
            }
            return R.ok(null).setMsg("该标签名已存在").setCode(ResCode.OPERATION_REJECT);
        }
    }

    @ApiOperation(value = "根据标签名获取标签信息")
    @ApiImplicitParam(name = "tagName", value = "标签名", required = true, paramType = "query")
    @GetMapping("/get")
    public R getBlogTag(@NotBlank(message = "标签名不能为空") @RequestParam("tagName") String tagName) {
        List<BlogTag> tags = blogTagService.getBlogTagByName(tagName);
        if (!   tags.isEmpty()) {
            log.info("请求获取标签 {{}} 信息",tags);
            return R.ok(tags).setCode(ResCode.OPERATION_SUCCESS);
        }
        return R.failed("未查询到该标签").setCode(ResCode.OPERATION_FAIL);
    }

    @ApiOperation(value = "获取所有标签信息")
    @GetMapping("/get/all")
    public R getAllBlogTags(){
        List<BlogTag> tags =  blogTagService.list();
        return R.ok(tags).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "递增该标签的排序索引")
    @GetMapping("/incr")
    public R incrTgIndex(@RequestParam("id") String id){
        if (blogTagService.incrIndex(id)) {
            return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
        }
        return R.failed("操作失败").setCode(ResCode.OPERATION_FAIL);
    }

    @ApiOperation(value = "重置所有标签的排序索引")
    @GetMapping("/reset")
    public R resetTagIndex(){
        if (blogTagService.resetIndex()) return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
        return R.failed("操作失败").setCode(ResCode.OPERATION_FAIL);
    }

}
