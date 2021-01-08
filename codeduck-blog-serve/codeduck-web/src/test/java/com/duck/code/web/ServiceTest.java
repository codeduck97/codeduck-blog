package com.duck.code.web;

import com.duck.code.commons.entity.blog.BlogArticle;
import com.duck.code.web.mapper.BlogArticleMapper;
import com.duck.code.web.mapper.BlogSortMapper;

import com.duck.code.web.mapper.PictureMapper;
import com.duck.code.web.service.BlogArticleService;
import com.duck.code.web.service.BlogSortService;
import com.duck.code.web.service.BlogTagService;
import com.duck.code.web.vo.BlogArticleVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
    private BlogArticleService blogArticleService;

    @Resource
    private BlogSortMapper blogSortMapper;

    @Resource
    private BlogSortService blogSortService;

    @Resource
    private BlogTagService blogTagService;

    @Resource
    private BlogArticleMapper blogArticleMapper;

    @Test
    public void testSortDic(){
        Map<String, String> sortDic = blogSortService.getSortDic();
        sortDic.forEach((k,v) -> {
            System.out.println(k + "====" + v);
        });
    }

    @Test
    public void testMapper(){
        List<BlogArticleVO> allBlogs = blogArticleService.getAllBlogs(2, 2);
        System.out.println(allBlogs.size());
    }

    @Test
    public void test(){
        HashSet<Object> objects = new HashSet<>();
    }

    @Test
    public void testEx(){
        try {
            System.out.println("0");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("2");
        }
    }

}
