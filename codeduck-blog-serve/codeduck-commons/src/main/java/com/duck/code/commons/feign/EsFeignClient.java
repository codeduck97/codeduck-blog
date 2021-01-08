package com.duck.code.commons.feign;

import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.entity.search.SearchParam;
import com.duck.code.commons.entity.search.EsBlogIndex;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-07 19:32
 **/
@FeignClient(name = "codeduck-search")
public interface EsFeignClient {

    @PostMapping("/api/es/blog/save")
    R saveOrUpdateBlogToEs(@RequestBody EsBlogIndex esBlogIndex);

    @DeleteMapping("/api/es/blog")
    R deleteBlogFromEs(@RequestBody EsBlogIndex esBlogIndex);

    @PostMapping("/api/es/blog/all")
    R searchAllBlogs(@RequestBody SearchParam searchParam);

    @PostMapping("/api/es/blog/search")
    R searchBlog(@RequestBody SearchParam searchParam);
}
