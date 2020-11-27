package com.duck.code.admin.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duck.code.commons.entity.sys.Permission;

/**
 * <p>
 * 后台权限表 Mapper 接口
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-27
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    JSONObject getUserPermission(String username);
}
