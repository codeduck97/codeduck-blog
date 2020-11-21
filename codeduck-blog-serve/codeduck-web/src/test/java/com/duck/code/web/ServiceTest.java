package com.duck.code.web;

import com.duck.code.web.mapper.BlogSortMapper;

import com.duck.code.web.mapper.PictureMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2020-11-20 20:28
 */
@SpringBootTest
public class ServiceTest {

    @Resource
    private PictureMapper pictureMapper;

    @Resource
    private BlogSortMapper blogSortMapper;

    @Test
    public void testMapper(){
        System.out.println(pictureMapper.selectById("23df656e06732cd32b61bfdbadb40fd5"));
    }

    @Test
    public void test(){
        System.out.println("000000");
    }

}
