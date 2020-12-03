package com.duck.code.admin.mapper;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.duck.code.commons.entity.sys.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Code Duck
 * @since 2020-10-20
 */
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * desc: 根据用户名查询用户角色名称
     * <p>
     *
     * @param
     * @return
     */
    @Select("SELECT role_name FROM sys_user u, sys_role r WHERE u.`role_id`= r.`id` AND u.`username`=#{username}")
    String queryRoleNameByUsername(@Param("username") String username);

    /**
     * desc: 查询所有用户信息
     * <p>
     *
     * @param page
     */
    IPage<Admin> queryAllUser(IPage page);

}
