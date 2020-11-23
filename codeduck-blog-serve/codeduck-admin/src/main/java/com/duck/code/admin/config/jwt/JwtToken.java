package com.duck.code.admin.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: codeduck-auth
 * @description:
 * @author: Code Duck
 * @create: 2020-09-30 18:44
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1900286977895826147L;

    /**
     * Token
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
