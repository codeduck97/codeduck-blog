package com.duck.code.commons.entity.search;

import lombok.Data;

import java.util.List;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-08 14:03
 */
@Data
public class SearchBlogResult {

    List<EsBlogIndex> blogIndexList;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页码
     */
    private Integer totalPages;
}
