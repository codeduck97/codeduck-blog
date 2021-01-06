package com.duck.code.admin.utils;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: codeduck-auth
 * @description: 通用方法工具类
 * @author: Code Duck
 * @create: 2020-09-27 17:20
 **/
public class AdminUtil {

    /**
     * 密码加密 salt
     */
    public static final String PRIMARY_KEY = "code-duck";


    /**
     * 加密用户明文密码
     * @param userPwd  用户明文密码
     * @return String
     */
    public static String md5UserPwd(String userPwd){
        String pwd = String.format("%s#%s",PRIMARY_KEY,userPwd);
        return DigestUtil.md5Hex(pwd);
    }

    /**
     * 处理非控制器类的输出 JSON 公共函数
     *
     * @param response
     * @param resultMap
     * @throws IOException
     */
    public static void outJson(ServletResponse response, Map<String, Object> resultMap) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            out.println(new JSONObject(resultMap).toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(null != out){
                out.flush();
                out.close();
            }
        }
    }
}
