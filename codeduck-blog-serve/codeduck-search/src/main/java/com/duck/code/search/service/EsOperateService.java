package com.duck.code.search.service;

import com.duck.code.commons.entity.search.EsBlogIndex;

import java.io.IOException;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-07 17:38
 **/
public interface EsOperateService {

    boolean saveOrUpdateBlogToBlogIndex01(EsBlogIndex esBlogIndex) throws IOException;

    boolean deleteBlogFromBlogIndex01(EsBlogIndex esBlogIndex) throws IOException;
}
