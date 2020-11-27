package com.duck.code.admin;

import com.duck.code.admin.mapper.BlogArticleMapper;
import com.duck.code.admin.service.AdminService;
import com.duck.code.admin.service.BlogArticleService;
import com.duck.code.admin.service.BlogSortService;
import com.duck.code.admin.service.PictureService;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AdminApplicationTests {

    @Resource
    private PictureService pictureService;

    @Resource
    private BlogSortService blogSortService;

    @Resource
    private AdminService adminService;

    @Resource
    private BlogArticleService blogArticleService;

    @Resource
    private BlogArticleMapper blogArticleMapper;


}
