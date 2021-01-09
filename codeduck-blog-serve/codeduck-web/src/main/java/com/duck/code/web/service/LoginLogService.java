package com.duck.code.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duck.code.commons.entity.sys.Admin;
import com.duck.code.commons.entity.sys.LoginLog;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Code Duck
 * @since 2021-01-09
 */
public interface LoginLogService extends IService<LoginLog> {

    void saveLoginLog(Admin user, HttpServletRequest request);
}
