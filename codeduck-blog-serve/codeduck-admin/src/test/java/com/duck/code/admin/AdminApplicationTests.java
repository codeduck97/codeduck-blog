package com.duck.code.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.duck.code.admin.mapper.AdminMapper;
import com.duck.code.admin.mapper.BlogArticleMapper;
import com.duck.code.admin.mapper.PermissionMapper;
import com.duck.code.admin.service.*;
import com.duck.code.commons.entity.blog.BlogArticle;
import com.duck.code.commons.entity.sys.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest
class AdminApplicationTests {


    @Resource
    private BlogSortService blogSortService;

    @Resource
    private AdminService adminService;

    @Resource
    private BlogArticleService blogArticleService;

    @Resource
    private BlogArticleMapper blogArticleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private PermissionService permissionService;

    @Resource
    private AdminMapper adminMapper;

    @Test
    public void tet() {
        BlogArticle blogArticle = new BlogArticle();
        blogArticle.setTitle("test");


    }


}
