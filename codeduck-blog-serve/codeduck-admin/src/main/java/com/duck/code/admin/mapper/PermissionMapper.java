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

    List<Permission> findUserPermissions(@Param("username") String username);
}
