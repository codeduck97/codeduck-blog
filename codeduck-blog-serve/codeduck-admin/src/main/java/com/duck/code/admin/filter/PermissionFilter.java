package com.duck.code.admin.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.duck.code.admin.utils.AdminUtil;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @program: code duck-auth
 * @description: 权限校验过滤器
 * @author: Code Duck
 * @create: 2020-09-27 19:45
 **/
public class PermissionFilter extends AccessControlFilter {

    /**
     * 使用 ant通配符匹配可以支持restful api风格的url
     */
    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Map<String,Object> resultMap = new HashMap<String, Object>();

        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        String url = httpRequest.getRequestURI();//获取URI
        String basePath = httpRequest.getContextPath();//获取basePath
        if (url != null && url.startsWith(basePath)){
            url = url.replaceFirst(basePath, "");
        }

        //获取请求头部的token值，解析用户名，如果是 admin 则拥有所有权限
        String token = httpRequest.getHeader("Authorization");
        DecodedJWT decodeToken = JWT.decode(token);
        /**
         * 由于解析出来的parseToken 实质上是一个map集合   Map<String, Object> claims = new HashMap<String, Object>();
         * value是泛型Object
         * 1.当我们的value是字符串类型时，通过 (String)parseToken.get("username") 可以吧Object强制转换为String类型的
         * 2.当我们的value是Integer类型的 我们需要把它转为String类型，在通过Long.parseLong 把String转为 long类型的
         * (虽然map中取出的结果是 Object ，但其实质是 Integer ，故在将其强制转为 String 时会出现强制类型转换的异常,
         * 需要使用String.valueOf来转字符串）
         */
        String userId = String.valueOf(decodeToken.getClaim("userId"));
        String roleHandPermission = String.valueOf(decodeToken.getClaim("roleHandlePermission"));
        List<String> currentUserHandPermission = new ArrayList<String>();

        // 高效的数组转list集合
        Collections.addAll(currentUserHandPermission, roleHandPermission.split(","));

        if( Long.parseLong(userId) == 1 ) { // 超级管理员有所有的权限
            return Boolean.TRUE;
        }

        // 自定义方式进行权限判断，支持restful api
        boolean isPermitted = matchPath(currentUserHandPermission,url);
        if(isPermitted) {
            return Boolean.TRUE;
        }

        resultMap.put("code", 10002);
        resultMap.put("msg", "无操作权限");
        AdminUtil.outJson(servletResponse, resultMap);

        return Boolean.FALSE;
    }

    /**
     * 这个方法需要上面方法返回false时才会触发，如果上面方法返回true时，会继续执行下一个过滤器，如果没有下一个过滤器了
     * 会执行真正的操作（该干嘛干嘛）
     * 要是执行到这个方法了，一般直接返回false，进行无权限操作提示处理，终止整个URL请求验证过程
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return Boolean.FALSE;
    }

    private boolean matchPath(List<String> authUrl, String url) {

        for (int i = 0; i < authUrl.size() ; i++) {
            boolean  matchResult = MATCHER.match(authUrl.get(i), url);
            if( matchResult ) {
                return true;
            }
        }
        return false;
    }
}
