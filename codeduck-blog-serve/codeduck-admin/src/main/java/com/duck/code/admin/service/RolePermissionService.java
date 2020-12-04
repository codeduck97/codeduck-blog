package com.duck.code.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.sys.RolePermission;

import java.util.List;

/**
 * <p>
 * 角色-权限关联表 服务类
 * </p>
 *
 * @author Code Duck
 * @since 2020-12-01
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 根据角色id删除响应的权限信息
     *
     * @param roleId
     */
    boolean deleteRolePermissionByRoleId(String roleId);


    /**
     * 通过角色id获取角色 权限信息
     *
     * @param
     */
    List<RolePermission> getRolePermissionByRoleId(String roleId);
}
