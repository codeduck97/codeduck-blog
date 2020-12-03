package com.duck.code.admin.config.shiro;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.duck.code.admin.config.jwt.JwtHelper;
import com.duck.code.admin.config.redis.RedisConstant;
import com.duck.code.admin.config.redis.client.RedisClient;
import com.duck.code.admin.config.jwt.JwtToken;
import com.duck.code.admin.service.AdminService;
import com.duck.code.admin.service.PermissionService;
import com.duck.code.commons.entity.sys.Admin;
import com.duck.code.commons.entity.sys.Permission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.*;

/**
 * @program: codeduck-auth
 * @description: 对用户进行认证和授权
 * @author: Code Duck
 * @create: 2020-09-27 20:19
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Resource
    private RedisClient redisClient;

    @Resource
    private AdminService adminService;

    @Resource
    private PermissionService permissionService;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        DecodedJWT claim = JwtHelper.getClaim(token.toString());
        Map<String, Claim> claims = claim.getClaims();
        String username = claims.get("username").asString();
        List<Permission> permissions = permissionService.findUserPermissions(username);
        Set<String> permission = new HashSet<>();
        permissions.forEach(p -> permission.add(p.getPermissionCode()));

        //查询用户的权限
        // 为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permission);
        return authorizationInfo;
    }

    /**
     * 对用户进行认证操作: 即登录验证
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 获取token
        String token = (String) authenticationToken.getCredentials();
        if (StringUtils.isBlank(token)){
            throw new AuthenticationException("token不能为空");
        }

        // 获取用户信息
        DecodedJWT claim = JwtHelper.getClaim(token);
        Map<String, Claim> claims = claim.getClaims();
        String userId = claims.get("userId").asString();
        String username = claims.get("username").asString();
        Admin admin = adminService.getById(userId);
        if (admin == null){
            throw new AuthenticationException("该帐号不存在");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if ( JwtHelper.verifyToken(token) && redisClient.hasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN.getKey() + username)){
            // 获取RefreshToken的时间戳
            String refreshTokenGenerateTime = redisClient.get(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN.getKey() + username).toString();
            if ((claim.getClaim("currentTimeMillis").asString()).equals(refreshTokenGenerateTime)) {
                return new SimpleAuthenticationInfo(token, token, "userRealm");
            }
        }
        throw new AuthenticationException("token异常");
    }
}
