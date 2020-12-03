package com.duck.code.admin.exception;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.ResCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
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
        return R.failed("token异常，请重新登录").setCode(ResCode.TOKEN_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public R  authenticationExceptionHandler(AuthenticationException e) {
        log.info("捕获到异常{}",e.getMessage());
        return R.failed("token已失效，请重新登录").setCode(ResCode.TOKEN_EXPIRED);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public R unauthorizedExceptionHandler(UnauthorizedException e) {
        log.info("捕获到异常{{}}",e.getMessage());
        return R.failed("对不起，您没有权限进行此操作！").setCode(ResCode.OPERATION_REJECT);
    }

    @ExceptionHandler(FileUploadIOException.class)
    public R  fileUploadIOExceptionHandler(FileUploadIOException e) {
        log.info("捕获到异常{}",e.getMessage());
        return R.failed(e.getMessage()).setCode(ResCode.OPERATION_REJECT);
    }


    @ExceptionHandler(TokenExpiredException.class)
    public void TokenExpiredExceptionHandler(TokenExpiredException e){
        log.warn("token过期，准备刷新token-{}" + e.getMessage());
    }
}
