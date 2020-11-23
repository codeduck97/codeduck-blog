package com.duck.code.admin;

import com.duck.code.admin.service.AdminService;
import com.duck.code.admin.service.BlogSortService;
import com.duck.code.admin.service.PictureService;
import com.duck.code.admin.utils.CommonUtil;
import com.duck.code.commons.entity.pojo.Admin;
import org.junit.jupiter.api.Test;
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
    @Test
    void contextLoads() {
        String password = CommonUtil.md5UserPwd("123123");
        Admin jason = adminService.queryByNamePwd("jason", password);
        System.out.println(jason);
    }

}
