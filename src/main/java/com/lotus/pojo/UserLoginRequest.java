package com.lotus.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 123232323L;
    private String tel;
    private String pwd;
}