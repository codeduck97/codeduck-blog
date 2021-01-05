package com.duck.code.commons.exception;

import com.baomidou.mybatisplus.extension.api.R;
import com.duck.code.commons.constant.ResCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;

/**
 * @program: codeduck-blog-serve
 * @description: 参数异常处理类
 * @author: Code Duck
 * @create: 2020-10-24 20:13
 */
@RestControllerAdvice
@Slf4j
public class ParameterCalibration {

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R handleMethodArgumentNotValidException(Exception exception) {
        StringBuilder errorInfo = new StringBuilder();
        BindingResult bindingResult=null;
        if(exception instanceof MethodArgumentNotValidException){
            bindingResult= ((MethodArgumentNotValidException)exception).getBindingResult();
        }
        if(exception instanceof BindException){
            bindingResult= ((BindException)exception).getBindingResult();
        }
        for(int i = 0; i < bindingResult.getFieldErrors().size(); i++){
            if(i > 0){
                errorInfo.append(",");
            }
            FieldError fieldError = bindingResult.getFieldErrors().get(i);
            errorInfo.append(fieldError.getField()).append(" :").append(fieldError.getDefaultMessage());
        }
        log.error(errorInfo.toString());
        return  R.failed("参数异常：" + errorInfo.toString()).setCode(ResCode.OPERATION_REJECT);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public R  handleDefaultException(Exception exception) {
        log.error(exception.toString());
        return  R.failed("系统异常" + exception.toString()).setCode(ResCode.SERVICE_ERROR);
    }

}
