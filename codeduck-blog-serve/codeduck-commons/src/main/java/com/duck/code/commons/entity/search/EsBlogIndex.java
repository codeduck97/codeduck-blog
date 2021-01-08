package com.duck.code.commons.entity.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: codeduck-blog-serve
 * @description: Elasticsearch 博文索引
 * @author: Code Duck
 * @create: 2021-01-07 16:37
 */
@Data
public class EsBlogIndex {

    private String blogId;

    private String userId;

    private String title;

    private String coverImage;

    private String content;

    private String author;

    private String sort;

    private List<String> tags;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 博文可被索引的状态：0-不可被索引，1-可被索引
     */
    private Integer status;

    private Integer hits;
}
