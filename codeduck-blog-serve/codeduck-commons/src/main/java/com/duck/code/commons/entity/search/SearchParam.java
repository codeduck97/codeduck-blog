package com.duck.code.commons.entity.search;

import lombok.Data;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-08 14:21
 */
@Data
public class SearchParam {

    private String keyword;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    private Integer pageSize = 5;
}
