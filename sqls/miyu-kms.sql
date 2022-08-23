CREATE DATABASE IF NOT EXISTS miyu_kms CHARACTER SET UTF8MB4;
USE miyu_kms;
-- 用户表
CREATE TABLE IF NOT EXISTS T_USER
(
    USER_ID       BIGINT(20)    NOT NULL COMMENT '用户ID',
    USER_NAME     VARCHAR(255) NOT NULL UNIQUE COMMENT '用户名',
    USER_TYPE     VARCHAR(255) NOT NULL COMMENT '用户类型',
    USER_PASSWORD VARCHAR(255) NOT NULL COMMENT '密码',
    CREATED_TIME  DATETIME COMMENT '创建时间',
    CREATED_BY   BIGINT(20)          NOT NULL COMMENT '创建人',
    UPDATED_BY    BIGINT(20)   COMMENT '更新人',
    UPDATED_TIME  DATETIME     COMMENT '更新时间',
    USER_DESC     VARCHAR(255) NOT NULL COMMENT '用户描述',
    PRIMARY KEY (USER_ID)
) COMMENT = '用户表';
-- 租户表
CREATE TABLE IF NOT EXISTS T_TENANT
(
    TENANT_ID    BIGINT(20)          NOT NULL COMMENT '租户主键id',
    TENANT_NAME  VARCHAR(255) NOT NULL UNIQUE COMMENT '租户名称',
    TENANT_DES   VARCHAR(255) COMMENT '租户描述',
    CREATED_TIME DATETIME NOT NULL  COMMENT '创建时间',
    UPDATED_BY   BIGINT(20)    COMMENT '更新人',
    UPDATED_TIME DATETIME      COMMENT '更新时间',
    CREATED_BY   BIGINT(20)          NOT NULL COMMENT '创建人',
    PRIMARY KEY (TENANT_ID)
) COMMENT = '租户表';

-- 主密钥(对称算法)
CREATE TABLE IF NOT EXISTS T_MASTER_KEY
(
    MK_ID          BIGINT(20)          NOT NULL COMMENT '主密钥ID',
    MK_ALIAS       VARCHAR(255) NOT NULL UNIQUE COMMENT '别名',
    MK_DESC        VARCHAR(255) COMMENT '描述',
    MK_TYPE        VARCHAR(255) NOT NULL COMMENT '主密钥类型，是谁使用密钥',
    MK_ALGORITHM   VARCHAR(255) NOT NULL COMMENT '密钥算法类型',
    MK_PADDING     VARCHAR(255) NOT NULL COMMENT '填充',
    MK_MODE        VARCHAR(255) NOT NULL COMMENT '模式',
    MK_USER_ID     BIGINT(20)          NOT NULL COMMENT 'mk对应的用户',
    MK_INDEX_IN_HSM VARCHAR(255)  COMMENT '密钥在密钥机中的索引或别称',
    MK_KEYSPACE_ID VARCHAR(255) NOT NULL COMMENT '对应密钥空间ID',
    MK_MASTER_KEY LONGTEXT  COMMENT '主密钥，预留。可以以后存储主密钥密文',
    CREATED_TIME   DATETIME     NOT NULL COMMENT '创建时间',
    UPDATED_BY     VARCHAR(32)  COMMENT '更新人',
    CREATED_BY     BIGINT(20)   COMMENT '创建人',
    UPDATED_TIME   DATETIME     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (MK_ID)
) COMMENT = '主密钥，对称密钥';

-- 主密钥对（非对称算法）
CREATE TABLE IF NOT EXISTS T_MASTER_KEYPAIR
(
    MKP_ID          BIGINT(20)          NOT NULL COMMENT '主密钥ID',
    MKP_ALIAS       VARCHAR(255) NOT NULL UNIQUE COMMENT '别名',
    MKP_DESC        VARCHAR(255) COMMENT '描述',
    MKP_TYPE        VARCHAR(255) NOT NULL COMMENT '主密钥类型，是谁使用密钥',
    MKP_ALGORITHM   VARCHAR(255) NOT NULL COMMENT '算法类型',
    MKP_USER_ID     BIGINT(20)          NOT NULL COMMENT 'mk对应的user id',
    MKP_INDEX_IN_HSM VARCHAR(255)  COMMENT '密钥在密钥机中的索引或别称',
    MKP_KEYSPACE_ID VARCHAR(255) NOT NULL COMMENT '对应密钥空间ID',
    MKP_PUBLIC_KEY  LONGTEXT COMMENT '公钥',
    MKP_PRIVATE_KEY  LONGTEXT COMMENT '私钥，预留',
    CREATED_TIME    DATETIME     NOT NULL COMMENT '创建时间',
    UPDATED_BY      BIGINT(20)   COMMENT '更新人',
    UPDATED_TIME    DATETIME     COMMENT '更新时间',
    CREATED_BY      BIGINT(20)          NOT NULL COMMENT '创建人',
    PRIMARY KEY (MKP_ID)
) COMMENT = '主密钥，非对称密钥';

--  凭据表
CREATE TABLE IF NOT EXISTS T_CREDENTIALS
(
    CRE_ID       BIGINT(20)      NOT NULL COMMENT '凭据ID',
    CRE_CONTENT  VARCHAR(255) COMMENT '凭据内容',
    CRE_TYPE     VARCHAR(255) COMMENT '凭据类型',
    CREATED_TIME DATETIME NOT NULL COMMENT '创建时间',
    UPDATED_BY   BIGINT(20)  COMMENT '更新人',
    UPDATED_TIME DATETIME  COMMENT '更新时间',
    CREATED_BY   BIGINT(20)      NOT NULL COMMENT '创建人',
    PRIMARY KEY (CRE_ID)
) COMMENT = '凭据表';
-- 密码机信息表
CREATE TABLE IF NOT EXISTS T_HSM
(
    HSM_ID       BIGINT(20)          NOT NULL COMMENT '密码机ID',
    HSM_HOST     VARCHAR(255) NOT NULL COMMENT '密码机地址',
    HSM_PORT     BIGINT(20)          NOT NULL COMMENT '密码机端口',
    HSM_USER     VARCHAR(255) COMMENT '密码机用户名',
    HSM_PASSWORD VARCHAR(255) COMMENT '密码机用户的密码',
    HSM_TYPE     VARCHAR(255) NOT NULL COMMENT '密码机类型、硬件or软件',
    CREATED_BY   BIGINT(20)          NOT NULL COMMENT '创建人',
    CREATED_TIME DATETIME     NOT NULL COMMENT '创建时间',
    UPDATED_BY   BIGINT(20)   COMMENT '更新人',
    UPDATED_TIME DATETIME COMMENT '更新时间',
    PRIMARY KEY (HSM_ID)
) COMMENT = '密码机信息表';

-- 密钥空间表
CREATE TABLE IF NOT EXISTS T_KEYSPACE
(
    SPACE_ID      BIGINT(20) NOT NULL COMMENT '空间ID',
    SPACE_HSM_ID  BIGINT(20) NOT NULL COMMENT '该密钥空间下用户授权使用的密码机',
    SPACE_USER_ID BIGINT(20) NOT NULL COMMENT '密钥空间对应的用户ID',
    SPACE_DESC    VARCHAR(255) COMMENT '密钥空间说明',
    SPACE_STATUS  INT COMMENT '空间状态，启动、禁止等',
    CREATED_BY    BIGINT(20) COMMENT '创建人',
    CREATED_TIME  DATETIME COMMENT '创建时间',
    UPDATED_BY    BIGINT(20) COMMENT '更新人',
    UPDATED_TIME  DATETIME COMMENT '更新时间',
    PRIMARY KEY (SPACE_ID)
) COMMENT = '用户密钥空间，对用户密钥进行隔离，分场景使用，分密码机使用';

-- 密码机授权表
CREATE TABLE IF NOT EXISTS T_USER_HSM
(
    USER_HSM_ID      BIGINT(20)         NOT NULL COMMENT '租户号',
    USER_HSML_HSM_ID BIGINT(20)         NOT NULL COMMENT '密码机id',
    USER_HSM_USER_ID BIGINT(20)         NOT NULL COMMENT '用户id',
    CREATED_BY       VARCHAR(32) NOT NULL COMMENT '创建人',
    CREATED_TIME     DATETIME    NOT NULL COMMENT '创建时间',
    UPDATED_BY       VARCHAR(32) COMMENT '更新人',
    UPDATED_TIME     DATETIME    COMMENT '更新时间',
    PRIMARY KEY (USER_HSM_ID)
) COMMENT = '授权用户可以使用的HSM';

-- 添加默认管理员数据
INSERT INTO `T_USER` VALUES (1561629900118687746,'admin','ADMIN','$2a$10$zK3OSE4nctibSFupK7MiI.oFU.JIgB48BEwcTIS5kPDUk3HwzkipW','2022-08-22 16:22:36',-1,-1,'2022-08-22 16:22:36','管理员');