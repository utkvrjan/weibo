/*
 Navicat Premium Data Transfer

 Source Server         : 47.99.44.146
 Source Server Type    : MySQL
 Source Server Version : 50553
 Source Host           : 47.99.44.146:3306
 Source Schema         : weibo

 Target Server Type    : MySQL
 Target Server Version : 50553
 File Encoding         : 65001

 Date: 02/01/2021 21:38:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `cid` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uid` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wid` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sendTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `wid`(`wid`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES ('7a202386-9100-449e-bdac-7b41d8dae99f', '', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', '51c48f9f-be42-40bd-9ca1-1d3d11178486', '2020-04-07 23:18:21');
INSERT INTO `comments` VALUES ('7dd4d8ad-c9d8-43f8-9e10-8f57d8dcb0f1', 'hh233', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', '51c48f9f-be42-40bd-9ca1-1d3d11178486', '2020-04-11 14:13:31');
INSERT INTO `comments` VALUES ('7e4717d3-d7f5-434d-b518-e03eacbd0876', '哇哦', '1e3767ad-b566-472b-9795-b16b99fcc24c', 'c052f68c-dfb2-4c96-9236-288d4cdd4b5d', '2020-04-07 23:19:59');
INSERT INTO `comments` VALUES ('a80bde64-3b1c-43d8-8443-914c97b812d1', '', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', 'a32091c4-2825-4bcb-98fe-1004d2b4f416', '2020-04-11 14:12:49');

-- ----------------------------
-- Table structure for fabulous
-- ----------------------------
DROP TABLE IF EXISTS `fabulous`;
CREATE TABLE `fabulous`  (
  `fid` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uid` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wid` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `visible` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`fid`) USING BTREE,
  UNIQUE INDEX `uid`(`uid`, `wid`) USING BTREE,
  INDEX `wid`(`wid`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of fabulous
-- ----------------------------
INSERT INTO `fabulous` VALUES ('0f02b9a8-0906-4194-9bc1-4e72e76a9ea2', 'ceb402c6-db82-4096-b445-f12f794f72a1', 'c052f68c-dfb2-4c96-9236-288d4cdd4b5d', 0);
INSERT INTO `fabulous` VALUES ('3cdf1647-8df6-4c5a-829c-2aaac1cc7884', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', 'c052f68c-dfb2-4c96-9236-288d4cdd4b5d', 0);
INSERT INTO `fabulous` VALUES ('581a7866-9889-4b9e-8549-63bb4c755480', 'ceb402c6-db82-4096-b445-f12f794f72a1', '51c48f9f-be42-40bd-9ca1-1d3d11178486', 0);
INSERT INTO `fabulous` VALUES ('ced346d1-69fd-49e5-96a9-72cc23bc7ad8', 'ceb402c6-db82-4096-b445-f12f794f72a1', 'a32091c4-2825-4bcb-98fe-1004d2b4f416', 0);
INSERT INTO `fabulous` VALUES ('f3861c4f-cd6c-4c32-886f-dced623b325d', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', '51c48f9f-be42-40bd-9ca1-1d3d11178486', 1);

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
  `fid` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `follow` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关注者',
  `about` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被关注者',
  `visible` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`fid`) USING BTREE,
  UNIQUE INDEX `follow`(`follow`, `about`) USING BTREE,
  INDEX `about`(`about`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO `follow` VALUES ('5a2f5df2-d45c-4062-b491-e76180d870ee', '1e3767ad-b566-472b-9795-b16b99fcc24c', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', 0);
INSERT INTO `follow` VALUES ('7b9cb9ba-bcf1-42dc-b2a7-2339bee8eee4', '69fe79a4-477f-45f1-9fcd-e9e3bd3d3be2', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', 0);
INSERT INTO `follow` VALUES ('a4a47394-8034-41df-95dc-b8e4df3de704', 'ceb402c6-db82-4096-b445-f12f794f72a1', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', 0);

-- ----------------------------
-- Table structure for hotsearch
-- ----------------------------
DROP TABLE IF EXISTS `hotsearch`;
CREATE TABLE `hotsearch`  (
  `hid` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `word` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `count` int(11) NULL DEFAULT 0,
  `searchTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`hid`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hotsearch
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `mid` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sender` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `receiver` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mcontent` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `sendtime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`mid`) USING BTREE,
  INDEX `receiver`(`receiver`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('c1101b21-113f-4568-a141-fce51ca556d7', '3a99483d-2687-4254-8781-403ba4eff48b', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', '我来测试了', '2020-04-08 09:53:07');
INSERT INTO `message` VALUES ('d22a42ce-8bc2-4701-bbfa-6e8b4734651f', '3a99483d-2687-4254-8781-403ba4eff48b', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', '你好啊', '2020-04-08 09:22:45');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'default.png',
  `regTime` datetime NULL DEFAULT NULL,
  `loginTime` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '男',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('0043e417-72e7-4ff8-bdd9-0ecbd0fae561', 'weiguang', 'e10adc3949ba59abbe56e057f20f883e', 'chuxia@weiguang', '9e26d0be-e032-4a50-8ae6-c17a267b0ba0.jpg', '2020-03-27 20:54:48', '2020-04-12 08:52:13', '123@qq.com', '女');
INSERT INTO `user` VALUES ('076d2361-44bc-4580-8587-6f0496557efe', 'ff', '633de4b0c14ca52ea2432a3c8a5c4c31', 'ff', 'default.png', '2020-04-10 17:04:13', '2020-04-11 09:56:05', 'ff@123', '男');
INSERT INTO `user` VALUES ('16facee0-fb8b-4582-8ae6-8e08fba3adb1', 'dd', '1aabac6d068eef6a7bad3fdf50a05cc8', '青柠1', 'default.png', '2020-04-09 09:58:36', '2020-04-11 09:53:21', 'bb@123.com', '男');
INSERT INTO `user` VALUES ('17d1eef1-2f09-4d3d-93fd-b5f168aa1a7c', '彪哥', '202cb962ac59075b964b07152d234b70', '萝莉喵', 'default.png', '2020-03-31 17:21:52', '2020-04-12 09:56:34', '1@qq.com', '男');
INSERT INTO `user` VALUES ('1e3767ad-b566-472b-9795-b16b99fcc24c', '初夏', '202cb962ac59075b964b07152d234b70', '猪柳蛋', '37a14397-01af-4dfb-90b9-3c51f61af24e.png', '2020-03-30 15:54:05', '2020-04-08 00:00:10', '111@123.com', '女');
INSERT INTO `user` VALUES ('3a99483d-2687-4254-8781-403ba4eff48b', 'aa', '4124bc0a9335c27f086f24ba207a4912', '哎哟', 'default.png', '2020-04-08 08:26:28', '2020-04-08 09:52:48', 'aa@123', '女');
INSERT INTO `user` VALUES ('5419b034-6784-4188-afd7-5b10fe7917e7', 'ee', '08a4415e9d594ff960030b921d42b91e', 'åå', 'default.png', '2020-04-09 10:21:34', '2020-04-12 13:56:40', 'ee@123', '男');
INSERT INTO `user` VALUES ('56ffe0dd-f47c-48d0-ae68-97eb62a940df', 'cc', 'cbb3586665ebdbc6ebadd796e3ba5bcf', '小宝贝', 'default.png', '2020-04-09 09:37:41', '2020-04-12 11:56:15', 'cc@123', '男');
INSERT INTO `user` VALUES ('69fe79a4-477f-45f1-9fcd-e9e3bd3d3be2', 'bb', '21ad0bd836b90d08f4cf640b4c298e7c', '青柠', 'default.png', '2020-04-09 09:03:25', '2020-04-11 14:07:33', 'bb@123.com', '男');
INSERT INTO `user` VALUES ('8108f921-7985-485b-84f2-7094c6e506be', '喜洋洋', '202cb962ac59075b964b07152d234b70', '喜洋洋', 'default.png', '2020-04-09 11:45:47', '2020-04-10 11:27:01', 'ww@163.com', '男');
INSERT INTO `user` VALUES ('ceb402c6-db82-4096-b445-f12f794f72a1', '美美', '202cb962ac59075b964b07152d234b70', '美美', 'default.png', '2020-04-09 10:31:37', '2020-04-11 14:44:41', '123@qq.com', '女');
INSERT INTO `user` VALUES ('dee4df19-70a1-45ad-ba7a-d4a101ef23f5', 'gg', 'cbb3586665ebdbc6ebadd796e3ba5bcf', '小宝贝', 'default.png', '2020-04-10 17:55:51', '2020-04-10 15:14:15', 'cc@123', '男');

-- ----------------------------
-- Table structure for weibo
-- ----------------------------
DROP TABLE IF EXISTS `weibo`;
CREATE TABLE `weibo`  (
  `wid` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sender` char(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sendTime` datetime NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `video` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`wid`) USING BTREE,
  INDEX `sender`(`sender`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of weibo
-- ----------------------------
INSERT INTO `weibo` VALUES ('29e7451e-96a2-448e-a211-d2cc59bb3154', '1e3767ad-b566-472b-9795-b16b99fcc24c', '2020-03-30 16:10:04', '今天你快乐吗@', 'd3a3dd65-b94c-4cb0-8d75-2d452d84f84d.png', '7dba5a66-de8c-4ec1-9976-76f49fbe0f46.mp4');
INSERT INTO `weibo` VALUES ('51c48f9f-be42-40bd-9ca1-1d3d11178486', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', '2020-03-31 17:23:26', '今天天气不错哦', 'eb6a853c-5589-4b41-b17b-b3b47c02fb96.png', NULL);
INSERT INTO `weibo` VALUES ('a32091c4-2825-4bcb-98fe-1004d2b4f416', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', '2020-03-31 21:07:20', '小朋友你是否有很多问号', 'a00cbd97-1c7d-42c9-b227-8acdf07fd85a.png', NULL);
INSERT INTO `weibo` VALUES ('c052f68c-dfb2-4c96-9236-288d4cdd4b5d', '0043e417-72e7-4ff8-bdd9-0ecbd0fae561', '2020-04-07 23:17:42', '你笑起来真好看', 'b191f5fc-59b9-4336-be50-2dca0dbd06ef.png', NULL);

SET FOREIGN_KEY_CHECKS = 1;
