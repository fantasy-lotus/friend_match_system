-- auto-generated definition
create table user
(
    uid        bigint auto_increment
        primary key,
    uname      varchar(256) default '0'               not null,
    tel        varchar(11)  default '0'               not null,
    pwd        varchar(50)  default '0'               not null,
    email      varchar(50)  default '0'               not null,
    gender     tinyint(1)   default 0                 not null comment '0 - 男 1 - 女',
    userStatus tinyint(1)   default 0                 not null comment '0正常 -1禁止',
    tags       varchar(1024)                          null,
    createTime datetime     default CURRENT_TIMESTAMP null,
    updateTime datetime     default CURRENT_TIMESTAMP null,
    isDelete   tinyint(1)   default 1                 not null
)
    charset = utf8mb4;

create table lotus_match.tag
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

