package com.duck.code.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.sys.Permission;

import java.util.List;

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


    /**
     * desc: 获取角色用户角色列表
     * <p>
     *
     * @param
     * @return
     */
    List<JSONObject> getRoleList();


    /**
     * desc: 获取所有的权限信息
     * <p>
     *
     * @param
     * @return
     */
    List<JSONObject> getAllPermission();

    /**
     * desc: 添加角色
     * <p>
     *
     * @param
     * @return
     */
    boolean addRole(JSONObject jsonObject);

}
