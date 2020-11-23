package com.duck.code.admin.exception;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.ResCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

/**
 * @program: codeduck-auth
 * @description: 全局异常处理类
 * @author: Code Duck
 * @create: 2020-09-27 15:52
 **/
@RestControllerAdvice
@Slf4j
public class AdminExceptionHandler {

    @ExceptionHandler(CredentialsException.class)
    public R exceptionHandler(CredentialsException e) {
        log.info("捕获到异常{}",e.getMessage());
        return R.failed(e.getMessage()).setCode(ResCode.TOKEN_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public R  authenticationExceptionHandler(AuthenticationException e) {
        log.info("捕获到异常{}",e.getMessage());
        return R.failed(e.getMessage()).setCode(ResCode.TOKEN_EXPIRED);
    }
    @ExceptionHandler(FileUploadIOException.class)
    public R  fileUploadIOExceptionHandler(FileUploadIOException e) {
        log.info("捕获到异常{}",e.getMessage());
        return R.failed(e.getMessage()).setCode(ResCode.OPERATION_REJECT);
    }

    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public R handle401(UnauthenticatedException e) {
        return R.failed("无权访问(Unauthorized):当前Subject是匿名Subject，请先登录").setCode(ResCode.TOKEN_ERROR);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public void TokenExpiredExceptionHandler(TokenExpiredException e){
        log.warn("token过期，准备刷新token-{}" + e.getMessage());
    }
}
