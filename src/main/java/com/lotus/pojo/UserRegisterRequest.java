package com.lotus.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 密码
     */
    private String pwd;
    /**
     * 确认密码
     */
    private String confirmPwd;
}
