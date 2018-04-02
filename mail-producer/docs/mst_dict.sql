/*
Navicat MySQL Data Transfer

Source Server         : docker-master
Source Server Version : 50717
Source Host           : 192.168.249.128:3307
Source Database       : db-test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-03-07 10:13:47
*/

SET FOREIGN_KEY_CHECKS=0;

/*==============================================================*/
/* Table: MAIL_SEND1                                            */
/*==============================================================*/
create table MAIL_SEND1 
(
   SEND_ID              VARCHAR(40)          not null,
   SEND_TO              VARCHAR(40)          not null,
   SEND_USER_ID         VARCHAR(40)          not null,
   SEND_CONTENT         VARCHAR(400)         not null,
   SEND_PRIORITY        NUMERIC(10)          not null,
   SEND_COUNT           NUMERIC(10)          not null,
   SEND_STATUS          VARCHAR(10)          not null,
   REMARK               VARCHAR(200),
   VERSION              NUMERIC(10)          not null,
   UPDATE_BY            VARCHAR(40)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_MAIL_SEND1 primary key (SEND_ID)
);

/*==============================================================*/
/* Table: MAIL_SEND2                                            */
/*==============================================================*/
create table MAIL_SEND0
(
   SEND_ID              VARCHAR(40)          not null,
   SEND_TO              VARCHAR(40)          not null,
   SEND_USER_ID         VARCHAR(40)          not null,
   SEND_CONTENT         VARCHAR(400)         not null,
   SEND_PRIORITY        NUMERIC(10)          not null,
   SEND_COUNT           NUMERIC(10)          not null,
   SEND_STATUS          VARCHAR(10)          not null,
   REMARK               VARCHAR(200),
   VERSION              NUMERIC(10)          not null,
   UPDATE_BY            VARCHAR(40)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_MAIL_SEND2 primary key (SEND_ID)
);

/*==============================================================*/
/* Table: MST_DICT                                              */
/*==============================================================*/
create table MST_DICT 
(
   ID                   VARCHAR(40)          not null,
   CODE                 VARCHAR(40)          not null,
   NAME                 VARCHAR(40)          not null,
   STATUS               VARCHAR(10)          not null,
   constraint PK_MST_DICT primary key (ID)
);

/*==============================================================*/
/* Table: SYS_USER                                              */
/*==============================================================*/
create table SYS_USER 
(
   USER_ID              VARCHAR(40)          not null,
   PASSWORD             VARCHAR(40)          not null,
   REAL_NAME            VARCHAR(40)          not null,
   MAIL                 VARCHAR(40)          not null,
   PHONE_NUMBER         VARCHAR(40),
   CREATE_BY            VARCHAR(40)          not null,
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_BY            VARCHAR(40)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_SYS_USER primary key (USER_ID)
);





-- ----------------------------
-- Records of t_sys_dic_type
-- ----------------------------
INSERT INTO `mst_dict` VALUES ('1', 'goodCategory', '物品分类', '1');
INSERT INTO `mst_dict` VALUES ('2', 'express', '快递', '1');
INSERT INTO `mst_dict` VALUES ('3', 'water', '水', '0');
INSERT INTO `mst_dict` VALUES ('4', 'tea', '茶', '0');
INSERT INTO `mst_dict` VALUES ('5', 'orange', '橘子', '1');
INSERT INTO `mst_dict` VALUES ('6', 'apple', '苹果', '1');
