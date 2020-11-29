package com.duck.code.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.duck.code.admin.mapper.AdminMapper;
import com.duck.code.admin.mapper.PermissionMapper;
import com.duck.code.admin.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.commons.entity.sys.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

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

    @Resource
    private AdminMapper adminMapper;

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
     * desc: 获取角色用户角色列表
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public List<JSONObject> getRoleList() {
        return this.baseMapper.queryAllRole();
    }

    /**
     * desc: 获取所有的权限信息
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public List<JSONObject> getAllPermission() {
        return this.baseMapper.queryPermissionList();
    }

    /**
     * desc: 添加角色
     * <p>
     *
     * @param
     * @return
     */
    @Override
    public boolean addRole(JSONObject jsonObject) {
        permissionMapper.insertRole(jsonObject);
        permissionMapper.insertRolePermission(jsonObject.getString("roleId"), (List<Integer>) jsonObject.get("permissions"));
        return true;
    }

    /**
     * desc: 从数据库中查询用户权限信息
     *
     * @param
     * @return
     */
    private JSONObject getUserPermissionFromDB(String username) {
        JSONObject userPermission = permissionMapper.queryUserPermission(username);
        // 如果是超级管理员，则拥有整个系统的所有权限
        String roleName = adminMapper.queryRoleNameByUsername(username);
        if (roleName.equals("超级管理员")) {
            Set<String> menuList = permissionMapper.queryAllMenu();
            Set<String> permissionList = permissionMapper.queryALlPermissions();
            userPermission.put("menuList", menuList);
            userPermission.put("permissionList", permissionList);
        }
        return userPermission;
    }
}
