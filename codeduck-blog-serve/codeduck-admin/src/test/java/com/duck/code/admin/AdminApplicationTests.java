package com.duck.code.admin;

import com.duck.code.admin.service.PictureService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AdminApplicationTests {

    @Resource
    private PictureService pictureService;
    @Test
    void contextLoads() {
        System.out.println(pictureService.count());
    }

}
