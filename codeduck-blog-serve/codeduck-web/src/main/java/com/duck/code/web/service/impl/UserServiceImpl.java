package com.duck.code.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.commons.constant.SQLConstants;
import com.duck.code.commons.constant.SysConstants;
import com.duck.code.commons.entity.sys.Admin;
import com.duck.code.commons.utils.StringUtil;
import com.duck.code.web.entity.LoginBody;
import com.duck.code.web.mapper.UserMapper;
import com.duck.code.web.service.CacheService;
import com.duck.code.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @program: codeduck-blog-serve
 * @description:
 * @author: Code Duck
 * @create: 2021-01-09 14:19
 */
@Service
@Slf4j
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, Admin> implements UserService {

    @Resource
    private CacheService cacheService;


    @Override
    public String login(LoginBody loginBody) {
        StringBuilder token = new StringBuilder();

        return null;
    }

    @Override
    public Admin getUserByNameOrEmail(LoginBody loginBody) {
        String username = loginBody.getUsername();
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper -> wrapper.eq(SQLConstants.USERNAME, username).or().eq(SQLConstants.EMAIL, username));
        queryWrapper.gt(SQLConstants.STATUS, SysConstants.DISABLED);
        queryWrapper.last(SQLConstants.LIMIT_ONE);
        return super.getOne(queryWrapper);
    }

    @Transactional
    @Override
    public Admin register(LoginBody loginBody) {
            Admin admin = new Admin();
            admin.setEmail(loginBody.getEmail());
            admin.setPassword(StringUtil.md5UserPwd(loginBody.getPassword()));
            admin.setStatus(SysConstants.FREEZE);
            admin.setUsername(loginBody.getUsername());
            admin.setNickname(loginBody.getNickname());
            super.save(admin);
        return getUserByNameOrEmail(loginBody);
    }

    @Override
    public void activeAccount(String username, String email) {
        LoginBody loginBody = new LoginBody();
        loginBody.setEmail(email);
        loginBody.setUsername(username);
        Admin user = getUserByNameOrEmail(loginBody);
        user.setStatus(SysConstants.ENABLE);
        super.updateById(user);
    }

}
