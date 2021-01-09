package com.duck.code.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.*;
import com.duck.code.commons.entity.sys.Admin;
import com.duck.code.commons.utils.StringUtil;
import com.duck.code.web.config.rabbitmq.RabbitMqClient;
import com.duck.code.web.config.redis.RedisClient;
import com.duck.code.web.entity.LoginBody;
import com.duck.code.web.service.LoginLogService;
import com.duck.code.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private RabbitMqClient rabbitMqClient;

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
        if (user == null) {
            return CommonRes.fail("用户不存在！");
        }

        if (user.getStatus() == SysConstants.FREEZE){
            return CommonRes.fail("用户账户未激活,请前往邮箱激活！");
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
            // redis存储用户信息 key = USER_TOKEN-
            redisClient.set(RedisConstants.USER_TOKEN + StringPool.COLON + token , JSONObject.toJSONString(user), 604800);
            HashMap<String, String> map = new HashMap<>();
            map.put(SysConstants.TOKEN, token);
            log.info("用户已从前台登录{{}}", user);
            return CommonRes.success(map);
        } else {
            return CommonRes.fail("账号或密码错误");
        }
    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/register")
    public R register(@RequestBody LoginBody loginBody,HttpServletRequest request) {
        if (loginBody.getUsername().length() < Constants.NUM_FIVE || loginBody.getUsername().length() >= Constants.NUM_TWENTY || loginBody.getPassword().length() < Constants.NUM_FIVE || loginBody.getPassword().length() >= Constants.NUM_TWENTY) {
            return CommonRes.fail(ResMsg.PARAM_INCORRECT);
        }

        Admin user = userService.getUserByNameOrEmail(loginBody);

        if (user != null) {
            return CommonRes.fail(ResMsg.ACCOUNT_DISABLE);
        }

        Admin needActiveUser = userService.register(loginBody);

        // 生成随机激活的token
        String token = StringUtil.getUUID();
        // 过滤密码
        needActiveUser.setPassword("");

        // redis存储未激活用户信息 key = ACTIVATE_USER:token
        redisClient.set(RedisConstants.ACTIVATE_USER + StringPool.COLON + token, JSONObject.toJSONString(needActiveUser), SysConstants.ONE_HOURS);

        rabbitMqClient.sendActivateEmail(needActiveUser, token);

        return CommonRes.success(loginBody);
    }

    @ApiOperation(value = "激活用户账号", notes = "激活用户账号")
    @GetMapping("/active/{token}")
    public R bindUserEmail(@PathVariable("token") String token){
        String userInfo = (String) redisClient.get(RedisConstants.ACTIVATE_USER + StringPool.COLON + token);

        if (StringUtils.isEmpty(userInfo)) {
            return CommonRes.fail(ResMsg.INVALID_TOKEN);
        }

        // JSON字符串转Java对象
        Admin user = JSONObject.parseObject(userInfo, Admin.class);
        user.setStatus(SysConstants.ENABLE);
        userService.updateById(user);
        return CommonRes.success(SysConstants.ACCOUNT_ACTIVATED);
    }

    @ApiOperation(value = "退出登录", notes = "退出登录", response = String.class)
    @PostMapping(value = "/logout")
    public R logout(@RequestParam(name = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            return CommonRes.fail("token为空，登出失败");
        }
        redisClient.del(RedisConstants.ACTIVATE_USER + StringPool.COLON + token);
        return CommonRes.success("已退出登录");
    }

}
