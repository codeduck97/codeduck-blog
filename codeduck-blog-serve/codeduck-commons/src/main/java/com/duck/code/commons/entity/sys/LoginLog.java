package com.duck.code.commons.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @program: codeduck-blog-serve
 * @description: 登录信息记录
 * @author: Code Duck
 * @create: 2020-11-30 21:20
 */
@TableName("t_login_log")
@Data
public class LoginLog {
    /**
     * 用户 ID
     */
    private String username;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录地点
     */
    private String location;

    /**
     * ip地址
     */
    private String ip;
}