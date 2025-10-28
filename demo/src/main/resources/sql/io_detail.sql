/*
 Navicat Premium Dump SQL

 Source Server         : db
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : rshyl

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 28/10/2025 11:13:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for io_detail
-- ----------------------------
DROP TABLE IF EXISTS `io_detail`;
CREATE TABLE `io_detail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '明细ID (Primary Key)',
  `io_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '进出仓单号 (外键: io_header.no)',
  `material_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物料代码 (外键: material.code)',
  `qty` int NOT NULL COMMENT '进仓或出仓数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_io_detail`(`io_no` ASC, `material_code` ASC) USING BTREE,
  INDEX `material_code`(`material_code` ASC) USING BTREE,
  CONSTRAINT `io_detail_ibfk_1` FOREIGN KEY (`io_no`) REFERENCES `io_header` (`no`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `io_detail_ibfk_2` FOREIGN KEY (`material_code`) REFERENCES `material` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '物料进出仓明细表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
