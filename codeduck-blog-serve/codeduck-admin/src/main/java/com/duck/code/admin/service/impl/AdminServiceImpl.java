package com.duck.code.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.admin.config.jwt.JwtHelper;
import com.duck.code.admin.mapper.AdminMapper;
import com.duck.code.admin.mapper.UserRoleMapper;
import com.duck.code.admin.service.AdminService;
import com.duck.code.admin.service.PermissionService;
import com.duck.code.admin.service.UserRoleService;
import com.duck.code.commons.constant.Constants;
import com.duck.code.commons.entity.sys.Admin;
import com.duck.code.commons.entity.sys.UserRole;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {



    @Resource
    private UserRoleService userRoleService;

    @Resource
    private UserRoleMapper userRoleMapper;


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
    public IPage<Admin> getAdminList(long pageNum, long pageSize) {
        Page<Admin> page = new Page<>(pageNum, pageSize);
        return baseMapper.queryAllUser(page);
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

    @Override
    @Transactional
    public boolean addUser(Admin admin) {
        if (super.save(admin)) {
            setUserRole(admin.getId(),admin.getRoleId());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateUser(Admin admin) {
        if (super.updateById(admin)) {
            updateUserRole(admin.getId(),admin.getRoleId());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteUserById(String userId) {
        if (super.removeById(userId)) {
            this.userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,userId));
            return true;
        }
        return false;
    }

    private void updateUserRole(String userId, Integer roleId) {
        this.userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,userId));
        setUserRole(userId,roleId);
    }

    private void setUserRole(String userId, Integer roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(Long.valueOf(roleId));
        this.userRoleService.save(userRole);
    }

}
