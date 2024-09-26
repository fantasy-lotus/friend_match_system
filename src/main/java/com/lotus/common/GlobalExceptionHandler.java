package com.lotus.common;

import lombok.extern.slf4j.Slf4j;
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
    //其他异常
    @ExceptionHandler(Exception.class)
    public void exception(Exception e) {
        log.error(e.getMessage(), e);
    }
}
