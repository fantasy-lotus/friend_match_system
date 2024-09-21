package com.lotus.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult<T> implements Serializable {
    /**
     * 状态码
     */
    private int code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 通知
     */
    private String msg;
    /**
     * 状态码详情
     */
    private String description;

    public BaseResult(int code, T data, String msg) {
        this(code, data, msg, "");
    }

    public BaseResult(ErrorCode errorCode) {
        this(errorCode.getCode(),null,errorCode.getMsg(),errorCode.getDescription());
    }
}
