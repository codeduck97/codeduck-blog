package com.duck.code.admin.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.duck.code.admin.utils.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @program: code duck-auth
 * @description: JwtUtil工具类（生成、解析、校验token）
 * @author: Code Duck
 * @create: 2020-09-27 16:20
 **/
@Component
public class JwtHelper {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);

    /**
     * 生成token的密钥
     */
    private static  String encryptJWTKey;

    /**
     * access_token的有效时长(单位：秒)
     */
    private static String accessTokenExpireTime;

    @Value("${config.jwt.accessToken-expireTime}")
    public void setAccessTokenExpireTime(String accessTokenExpireTime){
        JwtHelper.accessTokenExpireTime = accessTokenExpireTime;
    }

    @Value("${config.jwt.encrypt-jwtKey}")
    public void setEncryptJWTKey(String encryptJWTKey) {
        JwtHelper.encryptJWTKey = encryptJWTKey;
    }

    /**
     * 使用 HS256算法生成 JWT令牌 -> token
     *
     * @Param: subject: 携带用户的相关信息(用户名、用户ID、用户角色对应的权限等不敏感的信息)
     * @return: token
     */
    public static String generateToken(Map<String,String> subject){

        try {
            // 生成token过期时间,此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(Long.parseLong(subject.get("currentTimeMillis")) + Long.parseLong(accessTokenExpireTime) * 1000);
            // 使用加密算法对秘钥进行加密
            Algorithm secret = Algorithm.HMAC256(encryptJWTKey);
            JWTCreator.Builder builder = JWT.create();
            // 设置载荷信息
            subject.forEach((k,v) -> {
                builder.withClaim(k, v);
            });
            return builder.withExpiresAt(date).sign(secret);
        } catch (Exception e) {
            logger.error("JwtToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得 token中的载荷信息，无需secret解密也能获得
     *
     * @Param: token
     * @return: claims
     */
    public static DecodedJWT getClaim(String token){
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException e) {
            logger.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 核实 token 信息
     *
     * @param token
     * @return
     */
    public static boolean verifyToken(String token){
        try {
            String test = getClaim(token) + Base64Util.encodeThrowsException("");
            Algorithm secret = Algorithm.HMAC256(encryptJWTKey);
            JWTVerifier verifier = JWT.require(secret).build();
            DecodedJWT verify = verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            logger.error("JWTToken认证解密出现JWTVerificationException异常:" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

}
