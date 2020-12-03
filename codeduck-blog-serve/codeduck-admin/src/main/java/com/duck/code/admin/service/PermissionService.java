package com.duck.code.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.sys.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     * 通过用户名获取用户的权限菜单
     *
     * @param
     */
    List<Permission> findUserPermissions(String username);

    /**
     * 获取权限菜单
     *
     * @param
     */
    Map<String, Object> getPermissionMenus();

}
