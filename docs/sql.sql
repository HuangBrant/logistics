/*
 Navicat MySQL Data Transfer

 Source Server         : 175.24.147.82
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 175.24.147.82:3306
 Source Schema         : logistics

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 13/12/2020 23:48:31
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
  `date` datetime NOT NULL COMMENT '生产日期',
  `expiration_date` int(11) NULL DEFAULT NULL COMMENT '保质期，天',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '售价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity
-- ----------------------------
INSERT INTO `commodity` VALUES (1, '111,111', 'aa', 'RPC', 'false', '2020-12-10 16:12:52', 180, 12.00);
INSERT INTO `commodity` VALUES (2, '234', 'bb', 'HB', 'true', '2020-12-13 16:24:20', 12, 43.00);

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
  `start_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity_total
-- ----------------------------
INSERT INTO `commodity_total` VALUES (1, 1, 1, 123, 12.00, '2020-12-11 15:53:32', '2020-12-11 15:53:35');
INSERT INTO `commodity_total` VALUES (2, 1, 2, 34, 35.00, '2020-12-02 15:53:48', '2020-12-11 15:53:51');
INSERT INTO `commodity_total` VALUES (3, 2, 1, 345, 43.00, '2020-12-03 16:23:36', '2020-12-13 16:23:38');
INSERT INTO `commodity_total` VALUES (4, 2, 2, 67, 212.00, '2020-12-06 16:23:55', '2020-12-13 16:23:58');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `generated_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生产地址',
  `date` datetime NULL DEFAULT NULL COMMENT '新品上架时间',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '阿迪达斯', '义乌', '2020-12-11 15:54:46', 1111.00);
INSERT INTO `goods` VALUES (2, '耐克', '义乌', '2020-12-11 15:55:08', 222.00);

-- ----------------------------
-- Table structure for logistics_total
-- ----------------------------
DROP TABLE IF EXISTS `logistics_total`;
CREATE TABLE `logistics_total`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` int(11) NOT NULL COMMENT '有效包裹总数',
  `invalid` int(11) NULL DEFAULT NULL COMMENT '异常包裹数',
  `loss` int(11) NULL DEFAULT NULL COMMENT '损失包裹数',
  `logistics_num` int(11) NULL DEFAULT NULL COMMENT '总物流数',
  `drop` int(11) NULL DEFAULT NULL COMMENT '丢失物流数',
  `receive_num` int(11) NULL DEFAULT NULL COMMENT '收到包裹数',
  `sent_num` int(11) NULL DEFAULT NULL COMMENT '发送包裹数',
  `date` datetime NULL DEFAULT NULL COMMENT '统计时间',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '统计文件路径',
  `used_time` bigint(20) NULL DEFAULT NULL COMMENT '统计耗时(秒)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of logistics_total
-- ----------------------------
INSERT INTO `logistics_total` VALUES (1, 12, 1, 2, 4123, 12, 234, 2134, '2020-12-11 15:17:37', 'DEWE', 123);
INSERT INTO `logistics_total` VALUES (2, 4123, 24, 23, 8987686, 23, 234, 234, '2020-12-13 15:18:01', 'd大师风范', 456);

SET FOREIGN_KEY_CHECKS = 1;
