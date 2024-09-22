package com.lotus.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN(40100,"用户没有登录",""),
    NOT_ADMIN(40101,"非管理员用户",""),
    SYSTEM_ERROR(50000,"系统报错",""),
    STORE_ERROR(50001,"存储错误","");
    private final int code;
    private final String msg;
    private final String description;
}
