package com.duck.code.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.sys.UserRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Code Duck
 * @since 2020-12-01
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 根据角色Id查询关联的用户id列表
     *
     * @param roleId
     */
    List<String> getUserIdsByRoleId(String roleId);

}
