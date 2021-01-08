package com.duck.code.search.service;

import com.duck.code.commons.entity.search.SearchBlogResult;
import com.duck.code.commons.entity.search.SearchParam;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-07 17:36
 **/
public interface EsSearchService {

    SearchBlogResult searchBlogByKeyword(SearchParam searchParam);

    SearchBlogResult searchAllBlogs(SearchParam searchParam);
}
