package com.duck.code.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.sys.Role;

import java.util.List;

/**
 * <p>
 * 后台角色表 服务类
 * </p>
 *
 * @author Code Duck
 * @since 2020-12-01
 */
public interface RoleService extends IService<Role> {

    /**
     * desc: 获取所有角色信息
     * <p>
     *
     * @param pageNum
     * @param pageSize
     */
    IPage<Role> getRolesInfo(long pageNum, long pageSize);

    /**
     * desc: 根据用户名获取用户角色信息
     * <p>
     *
     * @param username
     */
    List<Role> findUserRole(String username);

    /**
     * 新增角色
     *
     * @param role
     */
    void addRole(Role role);

    /**
     * 根据角色id删除角色相关信息
     *
     * @param roleId
     */
    boolean deleteRoles(String roleId);
}
