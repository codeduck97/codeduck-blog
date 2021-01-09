package com.duck.code.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.Constants;
import com.duck.code.commons.constant.SysConstants;
import com.duck.code.commons.entity.sys.Admin;
import com.duck.code.commons.constant.CommonRes;
import com.duck.code.commons.utils.StringUtil;
import com.duck.code.web.config.redis.RedisClient;
import com.duck.code.commons.constant.RedisConstants;
import com.duck.code.web.entity.LoginBody;
import com.duck.code.web.service.LoginLogService;
import com.duck.code.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @program: codeduck-blog-serve
 * @description: 用户登录注册控制器
 * @author: Code Duck
 * @create: 2021-01-09 13:10
 */
@Slf4j
@RestController
@Api(value = "登录管理相关接口", tags = {"登录管理相关接口"})
@Validated
@RequestMapping("/api/login")
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private LoginLogService loginLogService;

    @Resource
    private RedisClient redisClient;

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping
    public R login(@RequestBody LoginBody loginBody, HttpServletRequest request) {
        log.info("用户尝试登录{{}}", loginBody);

        Admin user = userService.getUserByNameOrEmail(loginBody);

        log.info("用户信息{{}}", user);
        if (user == null || user.getStatus() == SysConstants.DISABLED) {
            return CommonRes.fail("用户账户未激活,请前往邮箱激活！");
        }

        if (user.getStatus() == SysConstants.FREEZE){
            return CommonRes.fail("用户账户已冻结，请联系管理员！");
        }

        if (!StringUtils.isEmpty(loginBody.getPassword()) &&
                user.getPassword().equals(StringUtil.md5UserPwd(loginBody.getPassword()))) {
            // 更新用户登录时间次数
            user.setLastLoginTime(LocalDateTime.now());
            user.setLoginTimes(user.getLoginTimes() + 1);
            userService.updateById(user);

            // 保存登录记录
            loginLogService.saveLoginLog(user,request);

            // 生成token
            String token = StringUtil.getUUID();
            user.setPassword("");
            redisClient.set(RedisConstants.USER_TOKEN + Constants.SYMBOL_COLON, JSONObject.toJSONString(user), 604800);
            HashMap<String, String> map = new HashMap<>();
            map.put(SysConstants.TOKEN, token);
            log.info("用户已从前台登录{{}}", user);
            return CommonRes.success(map);
        } else {
            return CommonRes.fail("账号或密码错误");
        }
    }

    @PostMapping("/register")
    public R register(@RequestBody Admin admin) {
        log.info("用户信息{{}}", admin);
        return CommonRes.success(admin);
    }
}
