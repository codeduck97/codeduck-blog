package com.duck.code.commons.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: codeduck-auth
 * @description: 统一返回数据结果
 * @author: Code Duck
 * @create: 2020-09-27 15:54
 **/
@Data
public class ResResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;
    private Object data;

    public ResResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
