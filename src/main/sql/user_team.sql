create table user_team
(
    id         int auto_increment comment 'id'
        primary key,
    tagName    varchar(30)                        not null comment '标签名称',
    userId     int                                not null comment '用户id',
    teamId   int                                not null comment '队伍id',
    isCaptain   tinyint                            not null comment '1 - 是 0 - 不是',
    joinTime datetime default CURRENT_TIMESTAMP NOT NULL comment '加入时间',
    createTime datetime default CURRENT_TIMESTAMP not null,
    updateTime datetime default CURRENT_TIMESTAMP not null,
    isDelete   tinyint  default 0                 not null comment '1 - 删除'
)
    charset = utf8mb4;