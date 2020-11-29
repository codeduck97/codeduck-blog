package com.duck.code.admin.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duck.code.commons.entity.sys.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 后台权限表 Mapper 接口
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-27
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * desc: 根据用户名查询用户权限信息
     * <p>
     *
     * @param username
     * @return
     */
    JSONObject queryUserPermission(String username);


    /**
     * desc: 查询可访问的列表
     * <p>
     *
     * @param
     * @return
     */
    @Select("SELECT p.menu_code menuCode FROM sys_permission p ORDER BY p.id")
    Set<String> queryAllMenu();

    /**
     * desc: 查询所有的权限: 仅返回具体权限集合
     * <p>
     *
     * @param
     * @return
     */
    @Select("SELECT p.permission_code permissionCode FROM sys_permission p ORDER BY p.id")
    Set<String> queryALlPermissions();

    /**
     * desc: 获取所有用户角色列表
     * <p>
     *
     * @param
     * @return
     */
    List<JSONObject> queryAllRole();

    /**
     * desc: 查询所有权限信息
     * <p>
     *
     * @param
     * @return 返回所有的权限信息
     */
    List<JSONObject> queryPermissionList();


    /**
     * desc: 新增角色
     * <p>
     *
     * @param
     * @return
     */
    int insertRole(JSONObject jsonObject);

    /**
     * 批量插入角色的权限
     *
     * @param roleId      角色ID
     * @param permissions 权限
     */
    int insertRolePermission(@Param("roleId") String roleId, @Param("permissions") List<Integer> permissions);
}
