package com.duck.code.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.admin.mapper.RoleMapper;
import com.duck.code.admin.service.RolePermissionService;
import com.duck.code.admin.service.RoleService;
import com.duck.code.commons.entity.sys.Role;
import com.duck.code.commons.entity.sys.RolePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 后台角色表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-12-01
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public IPage<Role> getRolesInfo(long pageNum, long pageSize) {
        Page<Role> pageInfo = new Page<>(pageNum, pageSize);
        return super.page(pageInfo);
    }

    @Override
    public List<Role> findUserRole(String username) {
        return baseMapper.findUserRole(username);
    }

    @Override
    public void addRole(Role role) {
        this.save(role);
        String[] permissionIds = role.getPermissionId();
        setRolePermission(role, permissionIds);
    }

    @Override
    public boolean deleteRoles(String roleId) {
        // 删除角色对应的权限id
        if (rolePermissionService.deleteRolePermissionByRoleId(roleId)) {
            return super.removeById(roleId);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateRole(Role role) {
        role.setUpdateTime(LocalDateTime.now());
        if (super.updateById(role)) {
            rolePermissionService.deleteRolePermissionByRoleId(role.getId().toString());
            setRolePermission(role, role.getPermissionId());
            return true;
        }
        return false;
    }

    private void setRolePermission(Role role, String[] permissionIds) {
        Arrays.stream(permissionIds).forEach(permissionId -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(Long.valueOf(permissionId));
            this.rolePermissionService.save(rolePermission);
        });
    }
}
