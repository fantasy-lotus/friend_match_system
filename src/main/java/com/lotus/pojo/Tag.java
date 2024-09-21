package com.lotus.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName tag
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Serializable {
    /**
     * 标签id
     */
    private Integer id;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 上传者
     */
    private Integer userId;

    /**
     * 父标签id
     */
    private Integer parentId;

    /**
     * 1 - 是 0 - 不是
     */
    private Integer isParent;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 1 - 删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}