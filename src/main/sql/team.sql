create table team
(
    id         int auto_increment comment '队伍id'
        primary key,
    name    varchar(30)                        not null comment '队伍名称',
    userId     int                                not null comment '队长',
    maxNum   int                                not null comment '最大人数',
    exTime  datetime                                    comment '过期时间',
    status tinyint                      default 0 not null comment '0 - 私有 1 - 公开',
    pwd varchar(30)                                    comment '密码',
    description varchar(256)                           comment '描述',
    createTime datetime default CURRENT_TIMESTAMP not null,
    updateTime datetime default CURRENT_TIMESTAMP not null,
    isDelete   tinyint  default 0                 not null comment '1 - 删除'
)
    charset = utf8mb4;