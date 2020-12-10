/*
 Navicat Premium Data Transfer

 Source Server         : lijunchi
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 175.24.147.82:3306
 Source Schema         : logistics

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 10/12/2020 16:10:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for commodity
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签，逗号隔开',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '材质',
  `overdue` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否过期',
  `date` datetime(0) NOT NULL COMMENT '生产日期',
  `expirationDate` int(11) NULL DEFAULT NULL COMMENT '保质期，天',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '售价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for commodity_total
-- ----------------------------
DROP TABLE IF EXISTS `commodity_total`;
CREATE TABLE `commodity_total`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL COMMENT '商品id',
  `send_status` int(11) NULL DEFAULT NULL COMMENT '1.接收，2.发送',
  `receive` int(11) NULL DEFAULT NULL COMMENT '数量',
  `price` decimal(10, 2) NOT NULL COMMENT '运费',
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `generated_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产地址',
  `date` datetime(0) NULL DEFAULT NULL COMMENT '新品上架时间',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for logistics_info
-- ----------------------------
DROP TABLE IF EXISTS `logistics_info`;
CREATE TABLE `logistics_info`  (
  `aid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '物流单号',
  `cid` int(11) NOT NULL COMMENT '商品id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `start_time` datetime(0) NOT NULL COMMENT '发出时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '签收时间',
  `status` tinyint(2) NOT NULL COMMENT '签收状态',
  `positionid` int(11) NULL DEFAULT NULL COMMENT '位置id',
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for logistics_total
-- ----------------------------
DROP TABLE IF EXISTS `logistics_total`;
CREATE TABLE `logistics_total`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` int(11) NOT NULL COMMENT '有效包裹总数',
  `invalid` int(11) NULL DEFAULT NULL COMMENT '异常包裹数',
  `loss` int(11) NULL DEFAULT NULL COMMENT '损失包裹数',
  `logisticsNum` int(11) NULL DEFAULT NULL COMMENT '总物流数',
  `drop` int(11) NULL DEFAULT NULL COMMENT '丢失物流数',
  `receiveNum` int(11) NULL DEFAULT NULL COMMENT '收到包裹数',
  `sentNum` int(11) NULL DEFAULT NULL COMMENT '发送包裹数',
  `date` datetime(0) NULL DEFAULT NULL COMMENT '统计时间',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '统计文件路径',
  `used_time` datetime(0) NULL DEFAULT NULL COMMENT '统计耗时',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
