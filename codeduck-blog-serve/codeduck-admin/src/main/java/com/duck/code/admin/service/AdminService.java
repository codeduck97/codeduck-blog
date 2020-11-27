package com.duck.code.admin.service;

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
     * desc: 根据用户名获取用户的创建时间
     * <p>
     *
     * @param username
     * @return time
     */
    LocalDateTime getCreatedTime(String username);

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
    List<Admin> getAdminList(long pageNum, long pageSize);

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
     * desc: 根据用户名计算存在的用户个数
     * <p>
     *
     * @param username
     * @return
     */
    int countNumByName(String username);
}
