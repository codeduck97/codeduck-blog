package com.duck.code.admin.config.shiro;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.duck.code.admin.config.jwt.JwtHelper;
import com.duck.code.admin.config.redis.RedisConstant;
import com.duck.code.admin.config.redis.client.RedisClient;
import com.duck.code.admin.config.jwt.JwtToken;
import com.duck.code.admin.service.AdminService;
import com.duck.code.commons.entity.sys.Admin;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: codeduck-auth
 * @description: 对用户进行认证和授权
 * @author: Code Duck
 * @create: 2020-09-27 20:19
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private RedisClient redisClient;

    @Resource
    private AdminService adminService;

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 对用户进行授权操作
     *      * 在过滤器中调用 isPermitted()方法时，会触发该方法
     *      * 注：权限判断这部分没有用isPermitted（）方法来判断，而是在PermissionFilter过滤器中自定义了权限判断所以用不到这个方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    /**
     * 对用户进行认证操作: 即登录验证
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /**
         *  认证通过后，会触发PermissionFilter的isAccessAllowed方法，开始验证权限
         */
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
        throw new AuthenticationException("token失效或token异常");
    }
}
