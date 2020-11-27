package com.duck.code.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.admin.config.jwt.JwtHelper;
import com.duck.code.admin.mapper.AdminMapper;
import com.duck.code.admin.service.AdminService;
import com.duck.code.admin.service.PermissionService;
import com.duck.code.commons.constant.Constants;
import com.duck.code.commons.entity.sys.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-20
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private PermissionService permissionService;

    /**
     * desc: 根据用户名获取用户的创建时间
     * <p>
     *
     * @param username
     * @return time
     */
    @Override
    public LocalDateTime getCreatedTime(String username) {
        if (!StringUtils.isEmpty(username)) {
            QueryWrapper<Admin> wrapper = new QueryWrapper<>();
            wrapper.select("creation_time").eq("username", username);
            Admin admin = this.baseMapper.selectOne(wrapper);
            if (admin != null) return admin.getCreationTime();
        }
        return null;
    }

    /**
     * desc: 根据用户密码查询用户信息
     * <p>
     *
     * @param username
     * @param password
     * @return user
     */
    @Override
    public Admin queryByNamePwd(String username, String password) {
        Admin admin = this.baseMapper.selectOne(
                Wrappers.<Admin>query()
                        .eq("username", username)
                        .eq("password", password));
        return admin;
    }

    /**
     * desc: 更新用户最近登录时间
     * <p>
     *
     * @param admin
     * @return void
     */
    @Override
    public boolean updateLastLoginTime(Admin admin) {
        admin.setLastLoginTime(LocalDateTime.now());
        return super.updateById(admin);
    }


    /**
     * desc: 分页获取用户列表
     * <p>
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<Admin> getAdminList(long pageNum, long pageSize) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        Page<Admin> page = new Page<>(pageNum, pageSize);
        wrapper.select(Admin.class, i -> !i.getColumn().equals("password"));
        wrapper.orderByDesc("last_login_time");
        Page<Admin> adminPage = super.page(page, wrapper);
        return adminPage.getRecords();
    }

    /**
     * desc: 更新用户登录次数
     * <p>
     *
     * @param admin
     * @return
     */
    @Override
    public boolean updateLoginCount(Admin admin) {
        Integer count = admin.getLoginTimes();
        admin.setLoginTimes(++count);
        return super.updateById(admin);
    }

    /**
     * desc: 根据用户名判断用户是否存在
     * <p>
     *
     * @param username
     * @return
     */
    @Override
    public boolean existAdminByName(String username) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        if (super.getOne(wrapper) == null) {
            return false;
        }
        return true;
    }

    /**
     * desc: 根据用户名查询用户信息
     * <p>
     *
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByName(String username) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.select(Admin.class, i -> !i.getColumn().equals("password"));
        wrapper.eq("username", username);
        return super.getOne(wrapper);
    }

    /**
     * desc: 查询当前登录用户的权限等信息
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public JSONObject getUserInfo(String token) {
        // 解析token获取附加信息
        DecodedJWT claim = JwtHelper.getClaim(token);
        String username = claim.getClaim("username").asString();
        JSONObject userPermission = permissionService.getUserPermission(username);
        // 使用shiro session保存用户权限信息
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);

        return userPermission;
    }
}
