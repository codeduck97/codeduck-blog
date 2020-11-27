package com.duck.code.admin.controller.blog;


import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.admin.vo.BlogSortVO;
import com.duck.code.admin.service.BlogSortService;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.constant.ResMsg;
import com.duck.code.commons.entity.blog.BlogSort;
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

/**
 * <p>
 * 博客分类表 前端控制器
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/api/blog/sort")
@Slf4j
@Validated
@Api(value = "博文分类API", tags = {"博文分类 API"})
public class BlogSortController {

    @Resource
    private BlogSortService blogSortService;

    @ApiOperation(value = "删除博客分类", notes = "根据id删除")
    @ApiImplicitParam(name = "id", value = "分类id", paramType = "query", required = true)
    @DeleteMapping("/delete")
    public R deleteBlogSort(@NotBlank(message = "分类id不能为空") @RequestParam("id") String id) {

        if (!blogSortService.existBlogArticle(id)) {
            BlogSort sort = blogSortService.getById(id);
            if (blogSortService.removeById(id)) {
                log.warn("博客分类 {{}} 已被删除", sort);
                return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
            }
            return R.failed(ResMsg.OPERATION_FAIL).setCode(ResCode.OPERATION_FAIL);
        }
        return R.ok(null).setMsg(ResMsg.BLOG_UNDER_THIS_TAG).setCode(ResCode.OPERATION_REJECT);
    }

    @ApiOperation(value = "新增博客分类", notes = "注意sortIndex：排序权重，其数值不能小于0")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sortName", value = "分类名称", paramType = "query", required = true),
            @ApiImplicitParam(name = "sortIndex", value = "分类排序权重，默认值：0，最小值：0", required = true,
                    paramType = "query", defaultValue = "0")
    })
    @PostMapping("/add")
    public R addBlogSort(@NotBlank(message = "分类名不能为空") @RequestParam("sortName") String sortName,
                         @Min(value = 0, message = "最小值为0") @RequestParam("sortIndex") Integer sortIndex) {
        if (!blogSortService.existSortName(sortName)) {
            BlogSort blogSort = new BlogSort();
            blogSort.setSortName(sortName);
            blogSort.setSortIndex(sortIndex);
            if (blogSortService.save(blogSort)) {
                log.info("添加博客类别 {{}}", blogSortService.getBlogSortByName(sortName));
                return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
            }
            return R.failed(ResMsg.OPERATION_FAIL).setCode(ResCode.OPERATION_FAIL);
        }
        return R.failed("该类别已存在").setCode(ResCode.OPERATION_REJECT);
    }

    @ApiOperation(value = "更新博客分类", notes = "更新的对象必须有id、sortName、sortIndex")
    @PutMapping("/update")
    public R updateBlogSort(@Validated({Update.class}) @RequestBody BlogSortVO blogSortVO) {
        List<BlogSort> sorts = blogSortService.getBlogSortByName(blogSortVO.getSortName());
        // 根据未出现同名标签
        if (sorts.isEmpty()) {
            if (blogSortService.updateBlogSort(blogSortVO)) {
                log.info("博客分类已被更新：{{}} ",blogSortVO);
                return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
            }
            return R.failed(ResMsg.OPERATION_FAIL).setCode(ResCode.OPERATION_FAIL);
        } else {
            BlogSort blogSort = sorts.get(0);
            if (blogSort.getId().equals(blogSortVO.getId())) {
                if (blogSortService.updateBlogSort(blogSortVO)) {
                    log.info("博客标签已被更新：{{}}",blogSortVO);
                    return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
                }
                return R.failed(ResMsg.OPERATION_FAIL).setCode(ResCode.OPERATION_FAIL);
            }
            return R.ok(null).setMsg("该分类名已存在").setCode(ResCode.OPERATION_REJECT);
        }
    }

    @ApiOperation(value = "根据分类名查询分类")
    @ApiImplicitParam(name = "sortName", value = "分类名称", paramType = "query",required = true)
    @GetMapping("/get")
    public R getBlogSortByName(@NotBlank(message = "分类名sortName 不能为空") @RequestParam("sortName") String name) {
        List<BlogSort> blogSort = this.blogSortService.getBlogSortByName(name);
        if (!blogSort.isEmpty()) {
            log.info("请求获取分类 {{}} 信息",blogSort);
            return R.ok(blogSort).setCode(ResCode.OPERATION_SUCCESS);
        }
        return R.failed("该分类不存在").setCode(ResCode.OPERATION_FAIL);
    }

    @ApiOperation(value = "获取博客分类列表", notes = "分页获取博客分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码,默认值：1", paramType = "query", defaultValue = "1",required = true),
            @ApiImplicitParam(name = "pageSize", value = "页面大小,默认值：5", paramType = "query", defaultValue = "5",required = true)
    })
    @PostMapping("/list")
    public R getBlogSortList(@Min(value = 1, message = "当前页 pageNum >= 1") @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
                             @Min(value = 1, message = "页面大小 pageSize >= 1") @RequestParam(value = "pageSize", defaultValue = "5") Long pageSize) {
        List<BlogSortVO> blogSortList = this.blogSortService.getBlogSortList(pageNum, pageSize);
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("sorts", blogSortList);
        resMap.put("total", blogSortService.count());
        log.info("博客分类列表被请求，pageNum：{{}}，pageSize：{{}}",pageNum,pageSize);
        return R.ok(resMap).setCode(ResCode.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "获取所有分类信息")
    @GetMapping("/get/all")
    public R getAll(){
        List<BlogSort> all = blogSortService.list();
        return R.ok(all).setCode(ResCode.OPERATION_SUCCESS);
    }


    @ApiOperation(value = "递增该分类的排序索引")
    @GetMapping("/incr")
    public R incrSortIndex(@RequestParam("id") String id){
        if (blogSortService.incrIndex(id)) {
            return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
        }
       return R.failed("操作失败").setCode(ResCode.OPERATION_FAIL);
    }

    @ApiOperation(value = "重置所有分类的排序索引")
    @GetMapping("/reset")
    public R resetTagIndex(){
        if (blogSortService.resetIndex()) return R.ok(null).setCode(ResCode.OPERATION_SUCCESS);
        return R.failed("操作失败").setCode(ResCode.OPERATION_FAIL);
    }
}
