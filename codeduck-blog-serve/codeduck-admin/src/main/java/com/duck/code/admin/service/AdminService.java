package com.duck.code.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.sys.Admin;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-20
 */
public interface AdminService extends IService<Admin> {


    /**
     * desc: 根据用户密码查询用户
     * <p>
     *
     * @param username
     * @param password
     * @return user
     */
    Admin queryByNamePwd(String username, String password);

    /**
     * desc: 更新用户最近登录时间
     * <p>
     *
     * @param admin
     * @return void
     */
    boolean updateLastLoginTime(Admin admin);

    /**
     * desc: 分页获取用户列表
     * <p>
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<Admin> getAdminList(long pageNum, long pageSize);

    /**
     * desc: 更新用户登录次数
     * <p>
     *
     * @param admin
     * @return
     */
    boolean updateLoginCount(Admin admin);

    /**
     * desc: 根据用户名判断用户是否存在
     * <p>
     *
     * @param username
     * @return
     */
    boolean existAdminByName(String username);

    /**
     * desc: 根据用户名查询用户信息
     * <p>
     *
     * @param username
     * @return
     */
    Admin getAdminByName(String username);

    /**
     * 添加用户
     *
     * @param admin
     */
    boolean addUser(Admin admin);

    /**
     * 更新用户信息
     *
     * @param admin
     */
    boolean updateUser(Admin admin);

    /**
     * 删除用户信息及相关角色信息
     *
     * @param userId
     */
    boolean deleteUserById(String userId);
}
