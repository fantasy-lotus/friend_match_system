package com.lotus.common;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ResultUtils {
    public static <T> BaseResult<T> success(T data){
        return new BaseResult<>(0, data, "请求成功");
    }
    @Contract("_ -> new")
    public static @NotNull BaseResult<Object> error(ErrorCode errorCode){
        return new BaseResult<>(errorCode);
    }
    public static BaseResult<Object> error(int code, String message,String description){
        return new BaseResult<>(code,null,message,description);
    }
}
