package com.duck.code.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.sys.Permission;

/**
 * <p>
 * 后台权限表 服务类
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-27
 */
public interface PermissionService extends IService<Permission> {

    /**
     * desc: 根据用户名称 查询某用户的 角色 菜单列表 权限列表
     * <p>
     *
     * @param username
     * @return
     */
    JSONObject getUserPermission(String username);

}
