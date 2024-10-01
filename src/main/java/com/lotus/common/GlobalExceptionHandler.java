package com.lotus.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResult business(BusinessException e) {
        log.error(e.getMessage());
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public BaseResult httpMediaType(HttpMediaTypeNotSupportedException e) {
        return ResultUtils.error(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg(), "不支持的请求格式");
    }
    //其他异常
    @ExceptionHandler(Exception.class)
    public BaseResult exception(Exception e) {
        log.error(e.getMessage(), e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR.getCode(),"请求异常","系统报错");
    }
}
