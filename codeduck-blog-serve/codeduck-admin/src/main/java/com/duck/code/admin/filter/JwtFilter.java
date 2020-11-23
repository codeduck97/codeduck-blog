package com.duck.code.admin.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.duck.code.admin.config.jwt.JwtHelper;
import com.duck.code.admin.config.redis.RedisConstant;
import com.duck.code.admin.config.redis.client.RedisClient;
import com.duck.code.admin.config.jwt.JwtToken;
import com.duck.code.admin.utils.CommonUtil;
import com.duck.code.commons.constant.ResCode;
import com.duck.code.commons.constant.ResMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: code duck-auth
 * @description: JWT过滤器: 用于登录信息校验(token校验)
 * @author: Code Duck
 * @create: 2020-09-27 17:20
 **/
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Value("${config.jwt.refreshToken-expireTime}")
    private String refreshTokenExpireTime;

    @Resource
    private RedisClient redis;

    /**
     * shiro判断是否登录，在登录的情况下会走此方法，此方法返回true直接访问控制器
     * <p>
     * 做登录验证处理，如果返回true,则进入到PermissionFilter 进行权限验证
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        if (this.isLoginAttempt(request, response)) {
            try {
                // 如果请求中的token失效则会登录失败抛出异常
                this.executeLogin(request, response);
            } catch (Exception e) {
                String msg = e.getMessage();
                // 获取应用异常(该Cause是导致抛出此throwable(异常)的throwable(异常))
                Throwable throwable = e.getCause();
                if (throwable != null && throwable instanceof SignatureVerificationException) {
                    msg = "token或者密钥不正确(" + throwable.getMessage() + ")";
                } else if (throwable != null && throwable instanceof TokenExpiredException) {
                    // 该异常为JWT的AccessToken已过期，判断RefreshToken未过期就进行AccessToken刷新
                    if (this.refreshToken(request, response)) {
                        return true;
                    } else {
                        msg = "token已过期(" + throwable.getMessage() + ")";
                    }
                } else {
                    // 应用异常不为空
                    if (throwable != null) {
                        msg = "应用异常";
                    }
                }
                /**
                 * 处理错误的两种方式：
                 *  1、将非法请求转发到/401的 Controller处理，抛出自定义无权访问异常被全局捕捉再返回Response信息
                 *  2、无需转发，直接返回Response信息 一般使用第二种(更方便)
                 */
                Map<String, Object> resultMap = new HashMap<String, Object>();
                if (msg.equals("应用异常")) {
                    resultMap.put("code", ResCode.SERVICE_ERROR);
                    resultMap.put("msg", ResMsg.SERVER_ERROR);
                } else {
                    resultMap.put("code", ResCode.TOKEN_ERROR);
                    resultMap.put("msg", msg);
                }
                CommonUtil.outJson(response, resultMap);
                return false;
            }

        }
        return true;
    }

    /**
     * shiro 拒接登录。在没有登录的情况下会走此方法
     * <p>
     * 这个方法需要上面方法返回false时才会触发，如果上面方法返回true时，会继续执行下一个过滤器
     * 要是执行到这个方法了，一般直接返回false，进行无权限操作提示处理，终止整个URL请求验证过程
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        this.sendChallenge(request, response);
        return false;
    }

    /**
     * desc: 判断用户是否为尝试登陆
     * <p>
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        // 后续进行判断
        return true;
    }

    /**
     * desc: 进行AccessToken登录认证授权
     * <p>
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        String token = request.getParameter("token");
        JwtToken jwtToken = null;
        if (!StringUtils.isEmpty(token)) {
            // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
            jwtToken = new JwtToken(token);
        } else {
            jwtToken = new JwtToken(this.getAuthzHeader(request));
        }
        // 提交给UserRealm进行认证，如果认证错误则抛出异常并被捕获
        this.getSubject(request, response).login(jwtToken);
        return true;
    }

    /**
     * desc: 刷新AccessToken，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     * <p>
     *
     * @param request
     * @return response
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {

        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);
        // 获取当前token的荷载信息
        DecodedJWT claim = JwtHelper.getClaim(token);
        Map<String, Claim> claims = claim.getClaims();
        Map<String, String> claimsMap = new HashMap<>();
        claims.forEach((k, v) -> claimsMap.put(k, v.asString()));

        String username = claimsMap.get("username");
        String accessTokenGenerateTime = claimsMap.get("currentTimeMillis");

        // 判断 Redis中RefreshToken 是否存在
        if (redis.hasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN.getKey() + username)) {

            // 获取 RefreshToken 的时间戳
            String refreshTokenGenerateTime = redis.get(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN.getKey() + username).toString();

            // 获取当前AccessToken中的时间戳，与 RefreshToken 的时间戳对比，如果当前时间戳一致，进行AccessToken刷新
            if (accessTokenGenerateTime.equals(refreshTokenGenerateTime)) {

                // 获取当前最新时间戳
                String currentTimeMillis = String.valueOf(System.currentTimeMillis());

                // 更新 RefreshToken 的时间戳为当前最新时间
                redis.set(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN.getKey() + username, currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));

                // 更新 AccessToken 的时间戳为当前最新时间
                claimsMap.put("currentTimeMillis", currentTimeMillis);

                // 使用 claimMap 生成最新的token
                token = JwtHelper.generateToken(claimsMap);
                // 将新刷新的AccessToken再次进行Shiro的登录
                JwtToken jwtToken = new JwtToken(token);
                // 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获，如果没有抛出异常则代表登入成功，返回true
                this.getSubject(request, response).login(jwtToken);
                // 最后将刷新的AccessToken存放在Response的Header中的refresh-token字段返回
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader("refresh-token", token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                return true;
            }
        }
        return false;
    }
}
