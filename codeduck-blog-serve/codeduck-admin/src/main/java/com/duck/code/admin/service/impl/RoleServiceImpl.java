package com.duck.code.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.admin.mapper.RoleMapper;
import com.duck.code.admin.service.RoleService;
import com.duck.code.commons.entity.sys.Role;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台角色表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
