package com.duck.code.admin.service.impl;

import com.duck.code.admin.mapper.PermissionMapper;
import com.duck.code.admin.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.commons.entity.sys.Permission;
import org.springframework.stereotype.Service;

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

}
