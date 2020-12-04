package com.duck.code.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duck.code.admin.mapper.RolePermissionMapper;
import com.duck.code.admin.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.commons.entity.sys.RolePermission;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色-权限关联表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-12-01
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Override
    public boolean deleteRolePermissionByRoleId(String roleId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        return super.remove(wrapper);
    }

    @Override
    public List<RolePermission> getRolePermissionByRoleId(String roleId) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId,Long.valueOf(roleId)));
    }
}
