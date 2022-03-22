/*
 Navicat Premium Data Transfer

 Source Server         : local@172.16.156.128
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 172.16.156.128:3306
 Source Schema         : gulimall_admin

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 22/03/2022 09:14:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_CRON_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `CRON_EXPRESSION`, `TIME_ZONE_ID`) VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `JOB_CLASS_NAME`, `IS_DURABLE`, `IS_NONCONCURRENT`, `IS_UPDATE_DATA`, `REQUESTS_RECOVERY`, `JOB_DATA`) VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', NULL, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xEFBFBDEFBFBD0005737200156F72672E71756172747A2E4A6F62446174614D6170EFBFBDEFBFBDEFBFBDE8BFA9EFBFBDEFBFBD020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D6170EFBFBD08EFBFBDEFBFBDEFBFBDEFBFBD5D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013EFBFBD2EEFBFBD28760AEFBFBD0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507EFBFBDEFBFBDEFBFBD1660EFBFBD03000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686AEFBFBD014B597419030000787077080000017D1F41EFBFBD707874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673BEFBFBDEFBFBDCC8F23EFBFBD0200014A000576616C7565787200106A6176612E6C616E672E4E756D626572EFBFBDEFBFBDEFBFBD1D0BEFBFBDEFBFBDEFBFBD0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4EFBFBDEFBFBDEFBFBD3802000149000576616C75657871007E0013000000007800);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` (`SCHED_NAME`, `LOCK_NAME`) VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` (`SCHED_NAME`, `LOCK_NAME`) VALUES ('RenrenScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_SCHEDULER_STATE` (`SCHED_NAME`, `INSTANCE_NAME`, `LAST_CHECKIN_TIME`, `CHECKIN_INTERVAL`) VALUES ('RenrenScheduler', 'CloverdeMacBook-Pro.local1647779813617', 1647780611610, 15000);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `JOB_NAME`, `JOB_GROUP`, `DESCRIPTION`, `NEXT_FIRE_TIME`, `PREV_FIRE_TIME`, `PRIORITY`, `TRIGGER_STATE`, `TRIGGER_TYPE`, `START_TIME`, `END_TIME`, `CALENDAR_NAME`, `MISFIRE_INSTR`, `JOB_DATA`) VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', NULL, 1640219400000, 1640217600000, 5, 'WAITING', 'CRON', 1636936705000, 0, NULL, 2, 0xEFBFBDEFBFBD0005737200156F72672E71756172747A2E4A6F62446174614D6170EFBFBDEFBFBDEFBFBDE8BFA9EFBFBDEFBFBD020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D6170EFBFBD08EFBFBDEFBFBDEFBFBDEFBFBD5D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013EFBFBD2EEFBFBD28760AEFBFBD0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507EFBFBDEFBFBDEFBFBD1660EFBFBD03000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686AEFBFBD014B597419030000787077080000017D1F41EFBFBD707874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673BEFBFBDEFBFBDCC8F23EFBFBD0200014A000576616C7565787200106A6176612E6C616E672E4E756D626572EFBFBDEFBFBDEFBFBD1D0BEFBFBDEFBFBDEFBFBD0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4EFBFBDEFBFBDEFBFBD3802000149000576616C75657871007E0013000000007800);
COMMIT;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=251 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
BEGIN;
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (1, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-15 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (2, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-22 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (3, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-22 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (4, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-22 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (5, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-22 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (6, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-22 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (7, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-22 11:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (8, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-22 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (9, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-22 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (10, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-22 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (11, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-22 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (12, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-23 08:00:05');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (13, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-23 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (14, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-23 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (15, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-23 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (16, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-23 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (17, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-23 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (18, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-23 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (19, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-23 11:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (20, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-23 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (21, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-23 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (22, 1, 'testTask', 'renren', 0, NULL, 4, '2021-11-23 15:30:04');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (23, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-23 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (24, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-23 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (25, 1, 'testTask', 'renren', 0, NULL, 3, '2021-11-23 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (26, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-23 17:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (27, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-23 21:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (28, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-23 22:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (29, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-23 22:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (30, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-24 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (31, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-24 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (32, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-24 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (33, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-24 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (34, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-24 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (35, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-24 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (36, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-24 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (37, 1, 'testTask', 'renren', 0, NULL, 8, '2021-11-24 11:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (38, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-24 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (39, 1, 'testTask', 'renren', 0, NULL, 3, '2021-11-24 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (40, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-24 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (41, 1, 'testTask', 'renren', 0, NULL, 16, '2021-11-24 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (42, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-24 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (43, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-24 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (44, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-25 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (45, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-25 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (46, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-25 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (47, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-25 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (48, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-25 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (49, 1, 'testTask', 'renren', 0, NULL, 4, '2021-11-25 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (50, 1, 'testTask', 'renren', 0, NULL, 3, '2021-11-25 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (51, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-25 12:09:51');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (52, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-25 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (53, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-25 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (54, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-25 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (55, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-25 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (56, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-25 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (57, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-25 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (58, 1, 'testTask', 'renren', 0, NULL, 5, '2021-11-27 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (59, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-27 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (60, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-27 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (61, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-27 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (62, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-27 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (63, 1, 'testTask', 'renren', 0, NULL, 3, '2021-11-27 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (64, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-27 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (65, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-27 11:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (66, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-27 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (67, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-27 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (68, 1, 'testTask', 'renren', 0, NULL, 5, '2021-11-27 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (69, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-27 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (70, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-27 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (71, 1, 'testTask', 'renren', 0, NULL, 4, '2021-11-27 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (72, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-27 17:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (73, 1, 'testTask', 'renren', 0, NULL, 3, '2021-11-28 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (74, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-28 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (75, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-28 11:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (76, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-28 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (77, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-28 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (78, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-28 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (79, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-28 19:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (80, 1, 'testTask', 'renren', 0, NULL, 0, '2021-11-28 19:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (81, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-28 20:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (82, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-28 20:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (83, 1, 'testTask', 'renren', 0, NULL, 3, '2021-11-28 21:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (84, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-29 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (85, 1, 'testTask', 'renren', 0, NULL, 6, '2021-11-29 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (86, 1, 'testTask', 'renren', 0, NULL, 4, '2021-11-29 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (87, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-29 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (88, 1, 'testTask', 'renren', 0, NULL, 5, '2021-11-29 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (89, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-29 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (90, 1, 'testTask', 'renren', 0, NULL, 5, '2021-11-29 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (91, 1, 'testTask', 'renren', 0, NULL, 4, '2021-11-29 11:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (92, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-29 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (93, 1, 'testTask', 'renren', 0, NULL, 7, '2021-11-29 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (94, 1, 'testTask', 'renren', 0, NULL, 5, '2021-11-29 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (95, 1, 'testTask', 'renren', 0, NULL, 3, '2021-11-29 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (96, 1, 'testTask', 'renren', 0, NULL, 4, '2021-11-29 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (97, 1, 'testTask', 'renren', 0, NULL, 9, '2021-11-30 07:57:36');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (98, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-30 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (99, 1, 'testTask', 'renren', 0, NULL, 8, '2021-11-30 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (100, 1, 'testTask', 'renren', 0, NULL, 9, '2021-11-30 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (101, 1, 'testTask', 'renren', 0, NULL, 7, '2021-11-30 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (102, 1, 'testTask', 'renren', 0, NULL, 5, '2021-11-30 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (103, 1, 'testTask', 'renren', 0, NULL, 4, '2021-11-30 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (104, 1, 'testTask', 'renren', 0, NULL, 3, '2021-11-30 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (105, 1, 'testTask', 'renren', 0, NULL, 4, '2021-11-30 11:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (106, 1, 'testTask', 'renren', 0, NULL, 1, '2021-11-30 14:30:01');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (107, 1, 'testTask', 'renren', 0, NULL, 4, '2021-11-30 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (108, 1, 'testTask', 'renren', 0, NULL, 6, '2021-11-30 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (109, 1, 'testTask', 'renren', 0, NULL, 5, '2021-11-30 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (110, 1, 'testTask', 'renren', 0, NULL, 2, '2021-11-30 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (111, 1, 'testTask', 'renren', 0, NULL, 3, '2021-11-30 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (112, 1, 'testTask', 'renren', 0, NULL, 15, '2021-11-30 17:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (113, 1, 'testTask', 'renren', 0, NULL, 3, '2021-11-30 18:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (114, 1, 'testTask', 'renren', 0, NULL, 7, '2021-12-01 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (115, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-01 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (116, 1, 'testTask', 'renren', 0, NULL, 4, '2021-12-01 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (117, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-01 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (118, 1, 'testTask', 'renren', 0, NULL, 5, '2021-12-01 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (119, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-01 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (120, 1, 'testTask', 'renren', 0, NULL, 4, '2021-12-01 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (121, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-01 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (122, 1, 'testTask', 'renren', 0, NULL, 10, '2021-12-01 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (123, 1, 'testTask', 'renren', 0, NULL, 5, '2021-12-01 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (124, 1, 'testTask', 'renren', 0, NULL, 35, '2021-12-01 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (125, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-01 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (126, 1, 'testTask', 'renren', 0, NULL, 5, '2021-12-01 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (127, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-01 18:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (128, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-02 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (129, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-02 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (130, 1, 'testTask', 'renren', 0, NULL, 10, '2021-12-02 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (131, 1, 'testTask', 'renren', 0, NULL, 4, '2021-12-02 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (132, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-02 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (133, 1, 'testTask', 'renren', 0, NULL, 9, '2021-12-02 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (134, 1, 'testTask', 'renren', 0, NULL, 9, '2021-12-02 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (135, 1, 'testTask', 'renren', 0, NULL, 5, '2021-12-02 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (136, 1, 'testTask', 'renren', 0, NULL, 5, '2021-12-02 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (137, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-07 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (138, 1, 'testTask', 'renren', 0, NULL, 16, '2021-12-07 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (139, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-07 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (140, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-07 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (141, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-07 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (142, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-07 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (143, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-07 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (144, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-08 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (145, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-08 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (146, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-08 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (147, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-08 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (148, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-08 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (149, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-09 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (150, 1, 'testTask', 'renren', 0, NULL, 5, '2021-12-09 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (151, 1, 'testTask', 'renren', 0, NULL, 20, '2021-12-09 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (152, 1, 'testTask', 'renren', 0, NULL, 10, '2021-12-09 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (153, 1, 'testTask', 'renren', 0, NULL, 15, '2021-12-09 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (154, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-09 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (155, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-09 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (156, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-09 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (157, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-09 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (158, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-09 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (159, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-09 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (160, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-09 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (161, 1, 'testTask', 'renren', 0, NULL, 8, '2021-12-09 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (162, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-09 17:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (163, 1, 'testTask', 'renren', 0, NULL, 6, '2021-12-09 18:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (164, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-10 13:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (165, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-10 14:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (166, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-10 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (167, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-10 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (168, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-10 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (169, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-10 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (170, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-10 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (171, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-10 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (172, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-10 17:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (173, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-10 18:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (174, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-10 22:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (175, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-10 22:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (176, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-11 11:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (177, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (178, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-11 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (179, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (180, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (181, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-11 17:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (182, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 18:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (183, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 18:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (184, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 19:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (185, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 19:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (186, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 20:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (187, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-11 20:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (188, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 21:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (189, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 21:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (190, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 22:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (191, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-11 22:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (192, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-12 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (193, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-12 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (194, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-12 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (195, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-12 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (196, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-12 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (197, 1, 'testTask', 'renren', 0, NULL, 4, '2021-12-12 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (198, 1, 'testTask', 'renren', 0, NULL, 7, '2021-12-12 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (199, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-12 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (200, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-12 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (201, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-12 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (202, 1, 'testTask', 'renren', 0, NULL, 32, '2021-12-12 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (203, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-12 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (204, 1, 'testTask', 'renren', 0, NULL, 15, '2021-12-12 19:00:03');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (205, 1, 'testTask', 'renren', 0, NULL, 10, '2021-12-12 20:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (206, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-12 20:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (207, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-12 21:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (208, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-13 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (209, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-13 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (210, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-13 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (211, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-13 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (212, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-13 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (213, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-13 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (214, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-13 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (215, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-13 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (216, 1, 'testTask', 'renren', 0, NULL, 4, '2021-12-13 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (217, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-13 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (218, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-13 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (219, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-13 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (220, 1, 'testTask', 'renren', 0, NULL, 4, '2021-12-14 07:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (221, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-14 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (222, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-14 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (223, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-14 14:29:45');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (224, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-14 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (225, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-14 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (226, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-14 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (227, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-14 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (228, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-14 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (229, 1, 'testTask', 'renren', 0, NULL, 9, '2021-12-14 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (230, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-15 08:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (231, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-15 08:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (232, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-15 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (233, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-15 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (234, 1, 'testTask', 'renren', 0, NULL, 0, '2021-12-15 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (235, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-15 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (236, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-15 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (237, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-15 14:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (238, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-15 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (239, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-15 16:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (240, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-15 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (241, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-15 17:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (242, 1, 'testTask', 'renren', 0, NULL, 1, '2021-12-22 09:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (243, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-22 09:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (244, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-22 10:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (245, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-22 10:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (246, 1, 'testTask', 'renren', 0, NULL, 2, '2021-12-22 11:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (247, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-22 15:00:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (248, 1, 'testTask', 'renren', 0, NULL, 4, '2021-12-22 15:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (249, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-22 16:30:00');
INSERT INTO `schedule_job_log` (`log_id`, `job_id`, `bean_name`, `params`, `status`, `error`, `times`, `create_time`) VALUES (250, 1, 'testTask', 'renren', 0, NULL, 3, '2021-12-23 08:00:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha` (
  `uuid` char(36) NOT NULL COMMENT 'uuid',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统验证码';

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------
BEGIN;
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('0c895e6f-0874-44dc-8a54-63a38af7ad40', 'cpf5n', '2021-11-22 10:14:48');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('21020d46-afc1-488a-8320-adb8f35a99b1', 'wdwc4', '2021-11-22 10:14:47');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('484c9383-e7d9-4322-8326-75cc82889b5c', 'xynx8', '2022-01-01 16:45:21');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('4897fb30-26a3-4c57-8e0f-030ff09d18db', 'cddne', '2021-11-22 10:14:47');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('492166e3-b61f-4651-8e3d-77f8b0b23dde', 'my3c2', '2021-11-22 10:14:47');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('7d9ecf77-3b0a-4d9f-81b2-0ac543d33967', '4a4wa', '2022-03-17 09:07:00');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('99a85b05-7e2f-4218-884b-8017161dffb1', '54cm2', '2021-11-22 10:13:43');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('a6aa221c-9adb-4741-84cd-586742ed2cf5', '22m7p', '2021-11-22 10:14:47');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('ad3b63ed-9c4d-4544-889d-6401a62e6948', '6df2m', '2021-11-22 10:14:48');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('b36b574b-aa21-4435-85ae-395f6b41a4a6', 'nnf4a', '2021-11-22 10:14:48');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('c469f6fa-d505-45e8-8abe-a8691e5f8b5a', 'wwggd', '2022-01-07 14:57:14');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('c52ee0c2-009d-4396-80f6-5d7617290b40', '885dc', '2022-02-04 21:09:28');
INSERT INTO `sys_captcha` (`uuid`, `code`, `expire_time`) VALUES ('f5bb1af6-daa8-4a75-8a9f-ac90ef6215b0', 'b4b75', '2021-11-22 10:14:49');
COMMIT;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` (`id`, `param_key`, `param_value`, `status`, `remark`) VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', 0, '云存储配置信息');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` (`id`, `username`, `operation`, `method`, `params`, `time`, `ip`, `create_date`) VALUES (1, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":31,\"parentId\":0,\"name\":\"商品系统\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"editor\",\"orderNum\":0,\"list\":[]}]', 11, '0:0:0:0:0:0:0:1', '2021-11-22 08:08:43');
INSERT INTO `sys_log` (`id`, `username`, `operation`, `method`, `params`, `time`, `ip`, `create_date`) VALUES (2, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":32,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"product\",\"perms\":\"\",\"type\":1,\"icon\":\"\",\"orderNum\":0,\"list\":[]}]', 10, '0:0:0:0:0:0:0:1', '2021-11-22 08:09:30');
INSERT INTO `sys_log` (`id`, `username`, `operation`, `method`, `params`, `time`, `ip`, `create_date`) VALUES (3, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":32,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"product/category\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0,\"list\":[]}]', 11, '0:0:0:0:0:0:0:1', '2021-11-22 08:10:01');
INSERT INTO `sys_log` (`id`, `username`, `operation`, `method`, `params`, `time`, `ip`, `create_date`) VALUES (4, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":33,\"parentId\":31,\"name\":\"品牌管理\",\"url\":\"product/brand\",\"perms\":\"\",\"type\":1,\"icon\":\"editor\",\"orderNum\":0,\"list\":[]}]', 15, '0:0:0:0:0:0:0:1', '2021-11-23 15:49:10');
INSERT INTO `sys_log` (`id`, `username`, `operation`, `method`, `params`, `time`, `ip`, `create_date`) VALUES (5, 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '[{\"userId\":2,\"username\":\"CloverYou\",\"password\":\"c39bf3d8649f3f004abd14b7010bed96bd7405b201d75ac1796f287d1d786e2a\",\"salt\":\"CKc34LIUAFQ4e4Skn3rW\",\"email\":\"2621869236@qq.com\",\"mobile\":\"18933797903\",\"status\":1,\"roleIdList\":[],\"createUserId\":1,\"createTime\":\"Dec 13, 2021 8:57:30 AM\"}]', 122, '0:0:0:0:0:0:0:1', '2021-12-13 08:57:30');
INSERT INTO `sys_log` (`id`, `username`, `operation`, `method`, `params`, `time`, `ip`, `create_date`) VALUES (6, 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '[{\"userId\":2,\"username\":\"CloverYou\",\"salt\":\"CKc34LIUAFQ4e4Skn3rW\",\"email\":\"2621869236@qq.com\",\"mobile\":\"18933797903\",\"status\":0,\"roleIdList\":[],\"createUserId\":1}]', 17, '0:0:0:0:0:0:0:1', '2021-12-13 08:57:40');
INSERT INTO `sys_log` (`id`, `username`, `operation`, `method`, `params`, `time`, `ip`, `create_date`) VALUES (7, 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '[{\"userId\":2,\"username\":\"CloverYou\",\"salt\":\"CKc34LIUAFQ4e4Skn3rW\",\"email\":\"2621869236@qq.com\",\"mobile\":\"18933797903\",\"status\":0,\"roleIdList\":[],\"createUserId\":1}]', 16, '0:0:0:0:0:0:0:1', '2021-12-13 08:57:40');
INSERT INTO `sys_log` (`id`, `username`, `operation`, `method`, `params`, `time`, `ip`, `create_date`) VALUES (8, 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '[{\"userId\":2,\"username\":\"CloverYou\",\"salt\":\"CKc34LIUAFQ4e4Skn3rW\",\"email\":\"2621869236@qq.com\",\"mobile\":\"18933797903\",\"status\":1,\"roleIdList\":[],\"createUserId\":1}]', 16, '0:0:0:0:0:0:0:1', '2021-12-13 08:57:45');
INSERT INTO `sys_log` (`id`, `username`, `operation`, `method`, `params`, `time`, `ip`, `create_date`) VALUES (9, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":76,\"parentId\":73,\"name\":\"测试\",\"url\":\"\",\"perms\":\"\",\"type\":2,\"icon\":\"\",\"orderNum\":0,\"list\":[]}]', 19, '0:0:0:0:0:0:0:1', '2021-12-15 09:59:46');
INSERT INTO `sys_log` (`id`, `username`, `operation`, `method`, `params`, `time`, `ip`, `create_date`) VALUES (10, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[76]', 21, '0:0:0:0:0:0:0:1', '2021-12-15 10:01:21');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (1, 0, '系统管理', NULL, NULL, 0, 'system', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (5, 1, 'SQL监控', 'http://localhost:8080/renren-fast/druid/sql.html', NULL, 1, 'sql', 4);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (6, 1, '定时任务', 'job/schedule', NULL, 1, 'job', 5);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (29, 1, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (31, 0, '商品系统', '', '', 0, 'editor', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (32, 31, '分类维护', 'product/category', '', 1, 'menu', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (34, 31, '品牌管理', 'product/brand', '', 1, 'editor', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (37, 31, '平台属性', '', '', 0, 'system', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (38, 37, '属性分组', 'product/attrgroup', '', 1, 'tubiao', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (39, 37, '规格参数', 'product/baseattr', '', 1, 'log', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (40, 37, '销售属性', 'product/saleattr', '', 1, 'zonghe', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (41, 31, '商品维护', 'product/spu', '', 0, 'zonghe', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (42, 0, '优惠营销', '', '', 0, 'mudedi', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (43, 0, '库存系统', '', '', 0, 'shouye', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (44, 0, '订单系统', '', '', 0, 'config', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (45, 0, '用户系统', '', '', 0, 'admin', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (46, 0, '内容管理', '', '', 0, 'sousuo', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (47, 42, '优惠券管理', 'coupon/coupon', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (48, 42, '发放记录', 'coupon/history', '', 1, 'sql', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (49, 42, '专题活动', 'coupon/subject', '', 1, 'tixing', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (50, 42, '秒杀活动', 'coupon/seckill', '', 1, 'daohang', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (51, 42, '积分维护', 'coupon/bounds', '', 1, 'geren', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (52, 42, '满减折扣', 'coupon/full', '', 1, 'shoucang', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (53, 43, '仓库维护', 'ware/wareinfo', '', 1, 'shouye', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (54, 43, '库存工作单', 'ware/task', '', 1, 'log', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (55, 43, '商品库存', 'ware/sku', '', 1, 'jiesuo', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (56, 44, '订单查询', 'order/order', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (57, 44, '退货单处理', 'order/return', '', 1, 'shanchu', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (58, 44, '等级规则', 'order/settings', '', 1, 'system', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (59, 44, '支付流水查询', 'order/payment', '', 1, 'job', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (60, 44, '退款流水查询', 'order/refund', '', 1, 'mudedi', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (61, 45, '会员列表', 'member/member', '', 1, 'geren', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (62, 45, '会员等级', 'member/level', '', 1, 'tubiao', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (63, 45, '积分变化', 'member/growth', '', 1, 'bianji', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (64, 45, '统计信息', 'member/statistics', '', 1, 'sql', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (65, 46, '首页推荐', 'content/index', '', 1, 'shouye', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (66, 46, '分类热门', 'content/category', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (67, 46, '评论管理', 'content/comments', '', 1, 'pinglun', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (68, 41, 'spu管理', 'product/spu', '', 1, 'config', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (69, 41, '发布商品', 'product/spuadd', '', 1, 'bianji', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (70, 43, '采购单维护', '', '', 0, 'tubiao', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (71, 70, '采购需求', 'ware/purchaseitem', '', 1, 'editor', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (72, 70, '采购单', 'ware/purchase', '', 1, 'menu', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (73, 41, '商品管理', 'product/manager', '', 1, 'zonghe', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (74, 42, '会员价格', 'coupon/memberprice', '', 1, 'admin', 0);
INSERT INTO `sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (75, 42, '每日秒杀', 'coupon/seckillsession', '', 1, 'job', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`user_id`, `username`, `password`, `salt`, `email`, `mobile`, `status`, `create_user_id`, `create_time`) VALUES (1, 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', 1, 1, '2016-11-11 11:11:11');
INSERT INTO `sys_user` (`user_id`, `username`, `password`, `salt`, `email`, `mobile`, `status`, `create_user_id`, `create_time`) VALUES (2, 'CloverYou', 'c39bf3d8649f3f004abd14b7010bed96bd7405b201d75ac1796f287d1d786e2a', 'CKc34LIUAFQ4e4Skn3rW', '2621869236@qq.com', '18933797903', 1, 1, '2021-12-13 08:57:30');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_token` (`user_id`, `token`, `expire_time`, `update_time`) VALUES (1, 'a05eb4004c862085ba6d43dad05c2f6d', '2022-03-19 02:25:01', '2022-03-18 14:25:01');
INSERT INTO `sys_user_token` (`user_id`, `token`, `expire_time`, `update_time`) VALUES (2, 'b759898533ab0952837fd4a207a14ec7', '2021-12-14 22:50:59', '2021-12-14 10:50:59');
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` (`user_id`, `username`, `mobile`, `password`, `create_time`) VALUES (1, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');
COMMIT;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
