package com.duck.code.commons.entity.sys;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2020-12-03 15:15
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tree<T> {

    private String id;

    private String value;

    private String label;

    private String parentId;

    private String permission;

    private List<Tree<T>> children;

    private boolean hasParent = false;

    private boolean hasChildren = false;

    public void initChildren(){
        this.children = new ArrayList<>();
    }
}
