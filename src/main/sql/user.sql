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



