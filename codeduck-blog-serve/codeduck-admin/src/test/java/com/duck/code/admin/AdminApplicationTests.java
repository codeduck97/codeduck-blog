package com.duck.code.admin;

import com.alibaba.fastjson.JSONObject;
import com.duck.code.admin.mapper.BlogArticleMapper;
import com.duck.code.admin.mapper.PermissionMapper;
import com.duck.code.admin.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private PermissionService permissionService;

    @Test
    public void test(){
        List<JSONObject> jsonObjects = permissionService.getAllPermission();
        jsonObjects.forEach(System.out::println);
    }


}
