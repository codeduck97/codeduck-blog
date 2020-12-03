package com.duck.code.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duck.code.commons.entity.sys.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台角色表 Mapper 接口
 * </p>
 *
 * @author Code Duck
 * @since 2020-12-01
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> findUserRole(@Param("username") String username);

}
