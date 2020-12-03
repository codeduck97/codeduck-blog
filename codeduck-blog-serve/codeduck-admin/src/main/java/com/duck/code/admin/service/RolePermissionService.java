package com.duck.code.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.sys.RolePermission;

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
}
