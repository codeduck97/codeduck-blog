package com.duck.code.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.duck.code.admin.mapper.PermissionMapper;
import com.duck.code.admin.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.commons.entity.sys.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 后台权限表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-27
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * desc: 查询用户的 角色 菜单列表 权限列表
     *
     * {
     *     "roleId":"1",
     *     "nickname":"程序猿",
     *     "roleName":"添加用户管理员",
     *     "permissionList":[
     *         "user:list",
     *         "user:add"
     *     ],
     *     "userId":"awsd0b4f46344543121ad4811239c34d"
     * }
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public JSONObject getUserPermission(String username) {
        JSONObject userPermission = getUserPermissionFromDB(username);
        return userPermission;
    }

    /**
     * desc: 从数据库中查询用户权限信息
     *
     * @param
     * @return
     */
    private JSONObject getUserPermissionFromDB(String username) {
        JSONObject userPermission = permissionMapper.getUserPermission(username);
        // 如果是超级管理员，则拥有整个系统的所有权限

        return userPermission;
    }
}
