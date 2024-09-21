package com.lotus.common;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private final int code;
    private String description;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        description = "";
    }

    public BusinessException(ErrorCode errorCode, String description) {
        this(errorCode);
        this.description = description;
    }
}
