package com.lotus.common;

public class ResultUtils {
    public static <T> BaseResult<T> success(T data){
        return new BaseResult<T>(0,data,"请求成功");
    }
    public static BaseResult error(ErrorCode errorCode){
        return new BaseResult<>(errorCode);
    }
    public static BaseResult error(int code, String message,String description){
        return new BaseResult<>(code,null,message,description);
    }
}
