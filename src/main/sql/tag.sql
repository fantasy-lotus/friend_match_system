create table tag
(
    id         int auto_increment comment '标签id'
        primary key,
    tagName    varchar(30)                        not null comment '标签名称',
    userId     int                                not null comment '上传者',
    parentId   int                                not null comment '父标签id',
    isParent   tinyint                            not null comment '1 - 是 0 - 不是',
    createTime datetime default CURRENT_TIMESTAMP not null,
    updateTime datetime default CURRENT_TIMESTAMP not null,
    isDelete   tinyint  default 0                 not null comment '1 - 删除',
    constraint index_tagName
        unique (tagName)
)
    charset = utf8mb4;