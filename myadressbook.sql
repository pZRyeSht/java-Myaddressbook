/*
 Navicat Premium Data Transfer

 Source Server         : 数据库
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : myadressbook

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 19/12/2018 21:12:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for adressbook
-- ----------------------------
DROP TABLE IF EXISTS `adressbook`;
CREATE TABLE `adressbook`  (
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `birth` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `mail` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `other` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of adressbook
-- ----------------------------
INSERT INTO `adressbook` VALUES ('张三', '2001-01-02', '13354656496', '1254@6581.com', '无');
INSERT INTO `adressbook` VALUES ('李四', '2001-01-09', '13354656495', '1254@6582.com', '无');
INSERT INTO `adressbook` VALUES ('王五', '2001-01-05', '13356656496', '1254@6583.com', '无');
INSERT INTO `adressbook` VALUES ('陈六', '2001-01-06', '13454656496', '1254@6584.com', '无');
INSERT INTO `adressbook` VALUES ('赵七', '2001-01-04', '13394656496', '1254@6585.com', '无');

SET FOREIGN_KEY_CHECKS = 1;
