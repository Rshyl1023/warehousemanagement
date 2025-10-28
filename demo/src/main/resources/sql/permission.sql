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

 Date: 28/10/2025 11:14:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `person_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '人员代码 (外键: person.code)',
  `resource_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源代码 (外键: resource.code)',
  `has_permission` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否拥有权限',
  PRIMARY KEY (`person_code`, `resource_code`) USING BTREE,
  INDEX `resource_code`(`resource_code` ASC) USING BTREE,
  CONSTRAINT `permission_ibfk_1` FOREIGN KEY (`person_code`) REFERENCES `person` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `permission_ibfk_2` FOREIGN KEY (`resource_code`) REFERENCES `resource` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户权限授予表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
