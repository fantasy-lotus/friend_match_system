package com.lotus.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    /**
    * 用户ID
    */
    @TableId(type = IdType.AUTO)
    private long uid;
    /**
    * 用户昵称
    */
    private String uname;
    /**
    * 用户电话
    */
    private String tel;


    private String pwd;
    /**
    * 用户邮箱
    */

    private String email;
    /**
    * 性别
     * 0 - 男 1 - 女
    */
    private int gender;
    /**
    * 用户状态 -0正常 -1禁止
    */
    private int userStatus;

    /**
     * 用户标签
     */
    private String tags;
    /**
     * 用户身份
     * 0 - 普通 1 - 管理员
     */
    private int role;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 修改时间
    */
    private Date updateTime;
    /**
    * 是否删除 -1正常 -0删除
    */
    @TableLogic
    private int isDelete;
}
