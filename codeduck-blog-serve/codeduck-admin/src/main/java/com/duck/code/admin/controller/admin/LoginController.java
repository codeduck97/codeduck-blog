package com.duck.code.admin.controller.admin;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.admin.config.jwt.JwtHelper;
import com.duck.code.admin.config.redis.RedisConstant;
import com.duck.code.admin.config.redis.client.RedisClient;
import com.duck.code.admin.service.AdminService;
import com.duck.code.admin.utils.CommonUtil;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.constant.ResMsg;
import com.duck.code.commons.entity.sys.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Code Duck
 * @since 2020-09-27
 */
@RestController
@Slf4j
@Validated
@Api(value = "登录登出API", tags = {"登录登出 API"})
public class LoginController {

    @Resource
    private AdminService adminService;

    @Value("${config.jwt.refreshToken-expireTime}")
    private String refreshTokenExpireTime;

    @Resource
    private RedisClient redis;

    /**
     * desc: 使用用户名和密码进行登录
     * <p>
     * @param username
     * @param password
     * @return 用户名和token
     */
    @ApiOperation(value = "用户登录API",notes = "登录时不用传入token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",paramType = "query",value = "用户名",required = true),
            @ApiImplicitParam(name = "password",paramType = "query",value = "密码",required = true)
    })
    @PostMapping("/login")
    public R userLogin(@NotBlank(message = "用户名不能为空") @RequestParam("username") String username,
                       @NotBlank(message = "登录密码不能为空") @RequestParam("password") String password){
        Admin admin = adminService.queryByNamePwd(username, CommonUtil.md5UserPwd(password));

        if (admin == null){
            return R.failed(ResMsg.LOGIN_ERROR).setCode(ResCode.OPERATION_FAIL);
        }

        // 清除可能存在的shiro权限信息缓存
        if (redis.hasKey(RedisConstant.PREFIX_SHIRO_CACHE.getKey() + admin.getUsername())){
            redis.del(RedisConstant.PREFIX_SHIRO_CACHE.getKey() + admin.getUsername());
        }

        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        redis.set(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN.getKey() + admin.getUsername(),
                currentTimeMillis,
                Integer.parseInt(refreshTokenExpireTime));
        String key = RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN.getKey() + admin.getUsername();

        adminService.updateLastLoginTime(admin);
        adminService.updateLoginCount(admin);

        Map<String,String> claimsMap = new HashMap<>();
        claimsMap.put("currentTimeMillis",currentTimeMillis);
        claimsMap.put("userId", admin.getId());
        claimsMap.put("username", admin.getUsername());
        /**
         * 登录成功后，生成token（token由 当前token的生成时间戳、用户 id、用户名、操作权限组成）并返回结果：
         * token 页面权限数据 操作权限数据 用户名
         */
        String token = JwtHelper.generateToken(claimsMap);
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("username", admin.getUsername());
        resMap.put("token", token);
        log.info("用户: " + username + "已上线");
        return R.ok(resMap).setCode(ResCode.OPERATION_SUCCESS).setMsg("登录成功");
    }

    /**
     * desc: 登出接口
     * <p>
     * @param request
     * @return
     */
    @ApiOperation(value="登出后台API",notes = "退出登录时请求头必须携带Authorization令牌")
    @PostMapping("/api/logout")
    public R logout(HttpServletRequest request){
        try {
            String token = "";
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()){
                String key =  headerNames.nextElement();
                String value = request.getHeader(key);
                if ("Authorization".equalsIgnoreCase(key)) {
                    token = value;
                }
            }

            // 校验token
            if (StringUtils.isBlank(token)){
                return R.failed("该请求未携带凭证").setCode(ResCode.TOKEN_ERROR);
            }
            String username = JwtHelper.getClaim(token).getClaim("username").asString();
            if (StringUtils.isBlank(username)){
                return R.failed("token异常").setCode(ResCode.TOKEN_ERROR);
            }
            // 清除shiro权限信息缓存
            if (redis.hasKey(RedisConstant.PREFIX_SHIRO_CACHE + username)) {
                redis.del(RedisConstant.PREFIX_SHIRO_CACHE + username);
            }
            // 清除 RefreshToken
            redis.del(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + username);
            log.info("用户: " + username + "已下线");
            return R.ok(null).setCode(ResCode.OPERATION_SUCCESS).setMsg("已登出");

        } catch (Exception e) {
            e.printStackTrace();
            return R.failed(e.getMessage()).setCode(ResCode.OPERATION_FAIL);
        }
    }

    /**
     * desc: 由于使用jwt保存用户信息，为了避免修改了用户信息，不重新登录的话不会立即生效，刷新页面会调用这个接口，
     *       所以我们在此接口中重新获取用户信息并生成token值。这样的话，页面刷新就可以重新生成最新的token值，
     *       避免了重新登录来刷新token
     * <p>
     * @param
     * @return
     */
    @ApiOperation(value = "获取用户信息",notes = "请求头必须携带Authorization令牌")
    @GetMapping("/api/admin/info")
    public R userInfo(HttpServletRequest request){
        Map<String,String> resultMap = new HashMap<>();

        /**
         * 获取请求头部的token值
         */
        String token    = request.getHeader("Authorization");
        DecodedJWT claim = JwtHelper.getClaim(token);
        Admin adminInfo = adminService.getById(claim.getClaim("userId").asString());
        resultMap.put("avatar", adminInfo.getAvatar()); // 需要的是用户头像，暂时用id代替
        resultMap.put("name", adminInfo.getUsername());

        return R.ok(resultMap).setCode(10000);
    }
}
