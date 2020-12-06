package com.duck.code.file;

import com.duck.code.commons.entity.picture.Picture;
import com.duck.code.file.config.system.SysConfig;
import com.duck.code.file.service.PictureService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2020-12-05 17:26
 */
@SpringBootTest
public class ServiceTest {

    @Resource
    private PictureService pictureService;

    @Resource
    private SysConfig sysConfig;

    @Test
    public void testService(){
        System.out.println(sysConfig.getLocalDir());
    }
}
