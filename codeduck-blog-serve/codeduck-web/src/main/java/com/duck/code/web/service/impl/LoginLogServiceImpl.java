package com.duck.code.web.service.impl;

import com.duck.code.commons.entity.sys.Admin;
import com.duck.code.commons.entity.sys.LoginLog;
import com.duck.code.commons.utils.AddressUtil;
import com.duck.code.commons.utils.HttpContextUtil;
import com.duck.code.commons.utils.IPUtil;
import com.duck.code.web.mapper.LoginLogMapper;
import com.duck.code.web.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2021-01-09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Override
    @Transactional
    public void saveLoginLog(Admin user, HttpServletRequest request) {
        LoginLog loginLog = new LoginLog();
//        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IPUtil.getIpAddr(request);
        loginLog.setUsername(user.getUsername());
        loginLog.setIp(ip);
        loginLog.setLoginTime(user.getLastLoginTime());
        loginLog.setLocation(AddressUtil.getCityInfo(ip));
        this.save(loginLog);
    }
}
