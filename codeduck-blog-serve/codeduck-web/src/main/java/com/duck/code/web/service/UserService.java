package com.duck.code.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.sys.Admin;
import com.duck.code.web.entity.LoginBody;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-09 14:19
 **/
public interface UserService  extends IService<Admin> {


    String login(LoginBody loginBody);

    Admin getUserByNameOrEmail(LoginBody loginBody);

    Admin register(LoginBody loginBody);
}
