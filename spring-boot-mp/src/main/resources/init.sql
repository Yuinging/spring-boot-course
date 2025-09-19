CREATE TABLE `user_account`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`    varchar(50)  NOT NULL COMMENT '用户名',
    `password`    varchar(255) NOT NULL COMMENT '密码',
    `nickname`    varchar(50)  DEFAULT NULL COMMENT '昵称',
    `email`       varchar(100) DEFAULT NULL COMMENT '邮箱',
    `phone`       varchar(20)  DEFAULT NULL COMMENT '手机号',
    `avatar_url`  varchar(255) DEFAULT NULL COMMENT '头像URL',
    `status`      tinyint(1)   DEFAULT '1' COMMENT '状态：1-启用，0-禁用',
    `deleted`     tinyint(1)   DEFAULT '0' COMMENT '逻辑删除：1-已删除，0-未删除',
    `version`     int          DEFAULT '0' COMMENT '版本号（乐观锁）',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_username` (`username`) USING BTREE COMMENT '用户名唯一索引',
    UNIQUE KEY `uk_user_email` (`email`) USING BTREE COMMENT '邮箱唯一索引',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账户表';