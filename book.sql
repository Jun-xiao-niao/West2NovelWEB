/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : book

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 03/03/2022 12:47:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for kamigamecollection
-- ----------------------------
DROP TABLE IF EXISTS `kamigamecollection`;
CREATE TABLE `kamigamecollection`  (
  `novelID` int NOT NULL,
  `novelName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`novelID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of kamigamecollection
-- ----------------------------
INSERT INTO `kamigamecollection` VALUES (1, '太初');
INSERT INTO `kamigamecollection` VALUES (3, '圣墟');

-- ----------------------------
-- Table structure for kamigamehistory
-- ----------------------------
DROP TABLE IF EXISTS `kamigamehistory`;
CREATE TABLE `kamigamehistory`  (
  `novelID` int NOT NULL,
  PRIMARY KEY (`novelID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of kamigamehistory
-- ----------------------------

-- ----------------------------
-- Table structure for novel
-- ----------------------------
DROP TABLE IF EXISTS `novel`;
CREATE TABLE `novel`  (
  `novelID` int NOT NULL COMMENT '小说ID',
  `writerName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者ID',
  `novelName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说名',
  `novelTag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小说类型',
  `brief` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '小说简介',
  `fileURL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件URL',
  `pictureURL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片URL',
  `collection` int NOT NULL COMMENT '小说收藏量',
  PRIMARY KEY (`novelID`, `collection`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of novel
-- ----------------------------
INSERT INTO `novel` VALUES (1, '高楼大厦', '太初', '玄幻', '一树生的万朵花，天下道门是一家。法术千般变化，人心却亘古不变。', 'F:\\novelUploading\\novel\\太初.txt', 'F:\\novelUploading\\picture\\太初.jpg', 2534);
INSERT INTO `novel` VALUES (2, '爱潜水的乌贼', '诡秘之主', '玄幻', '蒸汽与机械的浪潮中，谁能触及非凡？历史和黑暗的迷雾里，又是谁在耳语？我从诡秘中醒来，睁眼看见这个世界：枪械，大炮，巨舰，飞空艇，差分机；魔药，占卜，诅咒，倒吊人，封印物……光明依旧照耀，神秘从未远离，这是一段“愚者”的传说。', 'F:\\novelUploading\\novel\\诡秘之主.txt', 'F:\\novelUploading\\picture\\诡秘之主.jpg', 3287);
INSERT INTO `novel` VALUES (3, '辰东', '圣墟', '玄幻', '在破败中崛起，在寂灭中复苏。沧海成尘，雷电枯竭，那一缕幽雾又一次临近大地，世间的枷锁被打开了，一个全新的世界就此揭开神秘的一角……', 'F:\\novelUploading\\novel\\圣墟.txt', 'F:\\novelUploading\\picture\\圣墟.jpg', 9512);
INSERT INTO `novel` VALUES (4, '血红', '开天录', '玄幻', '生存，很容易。生活，很艰难。我族，要的不是卑下的生存，而是昂首、高傲的生活。我族，誓不为奴！', 'F:\\novelUploading\\novel\\开天录.txt', 'F:\\novelUploading\\picture\\开天录.jpg', 82354);
INSERT INTO `novel` VALUES (5, '宅猪', '牧神记', '玄幻', '大墟的祖训说，天黑，别出门。大墟残老村的老弱病残们从江边捡到了一个婴儿，取名秦牧，含辛茹苦将他养大。这一天夜幕降临，黑暗笼罩大墟，秦牧走出了家门……做个春风中荡漾的反派吧！瞎子对他说。秦牧的反派之路，正在崛起！书友群：600290060，624672265，VIP群：663057414（有验证）普通群：424940671', 'F:\\novelUploading\\novel\\牧神记.txt', 'F:\\novelUploading\\picture\\牧神记.jpg', 1859);
INSERT INTO `novel` VALUES (6, '波斯少校', '在美国当警察的日子', '都市', '阿拉斯古猛镇，是当年美国西部牛仔淘金时遗留下来的偏僻古镇。每年的初秋，遮天蔽日的黑乌鸦会从四面八方聚集在这个镇子。每当乌鸦来临的时候，镇里的居民说，夜里，他们有时会听到地下传来一些奇怪的声音。因此，这个小镇又被人称为幽灵镇。三年前，一个中国特警教官在这里当了一名小警员，三年后，他成了镇子的最高治安官。我们的故事，就从他当上了美国式的派出所所长开始说起吧。', 'F:\\novelUploading\\novel\\在美国当警察的日子.txt', 'F:\\novelUploading\\picture\\在美国当警察的日子.jpg', 7485);
INSERT INTO `novel` VALUES (7, '会说话的肘子', '夜的命名术', '都市', '蓝与紫的霓虹中，浓密的钢铁苍穹下，数据洪流的前端，是科技革命之后的世界，也是现实与虚幻的分界。钢铁与身体，过去与未来。这里，表世界与里世界并存，面前的一切，像是时间之墙近在眼前。黑暗逐渐笼罩。可你要明白啊我的朋友，我们不能用温柔去应对黑暗，要用火。', 'F:\\novelUploading\\novel\\夜的命名术.txt', 'F:\\novelUploading\\picture\\夜的命名术.jpg', 78653);
INSERT INTO `novel` VALUES (8, '长卿还成都', '重燃2001', '都市', '投行大佬吴楚之，功成名就后回归校园，却意外重生，睁开眼发现自己身处高考考场，而监考老师正神色不善的向他走来……回到2001年，面对前世的发小、死党、梅白月光，吴楚之该怎样逆天改命去挽救他们前世的悲剧命运？商海沉浮，看前世投行巨擘如何凭借金手指鹰击长空，鱼翔浅底。青梅自小陪伴，天降绝世独立，红颜舍命相救，情路上，吴楚之又当如何抉择？书龄二十年的老书虫，书荒之时向经典致敬之作。老柳带着自己的意难平，你们带着酒、花生米、板凳就行，我争取让这个故事不留遗憾。', 'F:\\novelUploading\\novel\\重燃2001.txt', 'F:\\novelUploading\\picture\\重燃2001.jpg', 453);
INSERT INTO `novel` VALUES (9, '辰东', '深空彼岸', '都市', '浩瀚的宇宙中，一片星系的生灭，也不过是刹那的斑驳流光。仰望星空，总有种结局已注定的伤感，千百年后你我在哪里？家国，文明火光，地球，都不过是深空中的一粒尘埃。星空一瞬，人间千年。虫鸣一世不过秋，你我一样在争渡。深空尽头到底有什么？', 'F:\\novelUploading\\novel\\深空彼岸.txt', 'F:\\novelUploading\\picture\\深空彼岸.jpg', 4563);
INSERT INTO `novel` VALUES (10, '刀锋间影', '我家影帝是奶爸', '都市', '影帝穿越平行世界，一夜间多了两个身份。奶爸不好当，姐夫更不好当。为了给她们不一样的人生，李白说，做个全能奶爸又何妨？已经有完本百万作品《圣手之尊》，求订阅。', 'F:\\novelUploading\\novel\\我家影帝是奶爸', 'F:\\novelUploading\\picture\\我家影帝是奶爸.jpg', 7865);
INSERT INTO `novel` VALUES (11, '爱搓汤圆', '全球掠夺：开局融合暗夜之心', '科幻', '神秘的游戏世界，一次让百万玩家穿越，面临生死考验。这里有多如繁星的种族文明，科技、修行、异能……文明的碰撞，璀璨而血腥。做为曾经的神级盗贼，顾宇是空间裂隙中唯一的幸存者。当别人还在饿肚子的时候，顾宇开始吸收能量强化。当别人在为复兴文明努力的时候，顾宇点燃了自己独有的文明之火。当别人拼命杀怪的时候，顾宇已经带着一群小弟掠夺资源。“叮！你成功将虚空行者转化为暗空族特殊兵种，蓝魔首领，文明指数+5。”“叮！你用法则碎片，黑暗*1，腐蚀*1，土系*1，成功融合一枚技能源球，迟缓术。”“叮！你成功降临到文明之火大世界，现在冒险正式开始……”', 'F:\\novelUploading\\novel\\全球掠夺：开局融合暗夜之心.txt', 'F:\\novelUploading\\picture\\全球掠夺：开局融合暗夜之心.jpg', 485);
INSERT INTO `novel` VALUES (12, '我吃西红柿', '宇宙职业选手', '科幻', '2036年，人类第一次登上了荧火星。2052年，第一届世界武道大赛举行，这也是全球最高格斗赛事，全球为之狂热。世界级顶尖选手“枪魔”许景明，于二十六岁那年，带着征战赛事留下的一身伤病，选择了退役……一个风起云涌的新时代，开始了！', 'F:\\novelUploading\\novel\\宇宙职业选手.txt', 'F:\\novelUploading\\picture\\宇宙职业选手.jpg', 1523);
INSERT INTO `novel` VALUES (13, '远瞳', '黎明之剑', '科幻', '高文穿越了，但穿越的时候稍微出了点问题。在某个异界大陆上空飘了十几万年之后，他觉得自己可能需要一具身体才算是成为一个完整的穿越者，但他并没想到自己好不容易成功之后竟然还需要带着这具身体从棺材里爬出来，并且面对两个吓蒙了的曾曾曾曾……曾孙女。以及一个即将迎来纪元终结的世界。 ', 'F:\\novelUploading\\novel\\黎明之剑.txt', 'F:\\novelUploading\\picture\\黎明之剑.jpg', 4537);
INSERT INTO `novel` VALUES (14, '陈词懒调', '每天都离现形更近一步', '科幻', '风羿创业遇挫陷入困境时，姑祖母的私人管家带着一份巨额遗产找到他。风羿：“同辈的那么多，她老人家为什么留给我？”管家：“你长得最讨喜。风羿摸了下自己的锥子脸：“她老人家眼光真好！”然而，继承遗产之后，整个人都不对劲了。强吗？帅吗？狂吗？牛X吗？智商换的（bushi）。 ', 'F:\\novelUploading\\novel\\每天都离现形更近一步.txt', 'F:\\novelUploading\\picture\\每天都离现形更近一步.jpg', 86);
INSERT INTO `novel` VALUES (15, '言归正传', '余光', '科幻', '“我们一直生活在星辰的灰烬里。”', 'F:\\novelUploading\\novel\\余光.txt', 'F:\\novelUploading\\picture\\余光.jpg', 453);
INSERT INTO `novel` VALUES (16, '菠萝炒土豆', '我来自大秦朝', '悬疑', '三世徐长卿，种因得果，要么魂飞魄散，要么扬名立万……', 'F:\\novelUploading\\novel\\我来自大秦朝.txt', 'F:\\novelUploading\\picture\\我来自大秦朝.jpg', 458);
INSERT INTO `novel` VALUES (17, '机智的肥喵', '推理同好会', '悬疑', '我们“推理同好会”是一个正经的推理破案群！正经的！！正经的！我们的群成员可都是……成天作死的富二代、腹黑的吃货医生、装嫩的带娃阿姨、铁憨憨附体的名牌大学生、技术宅、吐槽达人……等一下，画风怎么就这么歪了啊！至于神马死而复生的凶手，神马黑夜中少女的狩猎者、还有啥血色弥漫的死亡讯息，这都不是事！从加入这个略有点中二和逗比的“推理同好会”群聊开始，魏雨涵的生活从此开始变得不一样……书友群：262022016。欢迎小伙伴们一起来玩儿~ ', 'F:\\novelUploading\\novel\\推理同好会.txt', 'F:\\novelUploading\\picture\\推理同好会.jpg', 37);
INSERT INTO `novel` VALUES (18, '我会修空调', '我的治愈系游戏', '悬疑', '警察同志，如果我说这是一款休闲治愈系游戏，你们信吗？', 'F:\\novelUploading\\novel\\我的治愈系游戏.txt', 'F:\\novelUploading\\picture\\我的治愈系游戏.jpg', 123);
INSERT INTO `novel` VALUES (19, '阎ZK', '镇妖博物馆', '悬疑', '世之反常为妖。物之性灵为精。魂之不散为诡。物之异常为怪。司隶校尉，旧称卧虎，汉武帝所设，治巫蛊之事，捕奸滑之徒。全球范围内的灵气和神秘复苏，人类摸索着走上修行道路，潜藏在传说中的妖精鬼怪一一浮现，阴影处仍旧有无数邪魔晃动，一间无人问津的博物馆，一面汉武帝时期的刻虎腰牌，让卫渊成为当代最后一位司隶校尉，带他前往古往今来诸多妖异之事。古今稀奇事，子不语怪力乱神，姑妄言之，姑妄听之。姑且斩之。一柄八面汉剑，斩尽魑魅魍魉。生死当定，天道存心。当最后卫渊终于能在和平岁月里，躺着木椅眯眼晒太阳的时候，背后的博物馆里已经封印了无数的妖魔鬼怪。', 'F:\\novelUploading\\novel\\镇妖博物馆.txt', 'F:\\novelUploading\\picture\\镇妖博物馆.jpg', 7);
INSERT INTO `novel` VALUES (20, '虾写', '雾都侦探', '悬疑', '本书上天入地，见鬼玩人，包含反恐、反毒、侦破、谍战、布局、阴谋、连环杀手、飞天大盗等等内容。本书背景为欧洲，不涉及任何歧视，不涉及任何与我国有关的话题，请勿对号入座。此外，虾欧板块与大家正常认识的欧陆板块在小部分细节上可能存在不同。特别注意：切勿尝试用英语翻译对话，因为作者不会英语。十X本VIP完本作品，信誉保证。 ', 'F:\\novelUploading\\novel\\雾都侦探.txt', 'F:\\novelUploading\\picture\\雾都侦探.jpg', 123);

-- ----------------------------
-- Table structure for uploadingnovel
-- ----------------------------
DROP TABLE IF EXISTS `uploadingnovel`;
CREATE TABLE `uploadingnovel`  (
  `novelID` int NOT NULL AUTO_INCREMENT,
  `writerName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `novelName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `novelTag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `brief` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `collection` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fileURL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pictureURL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`novelID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uploadingnovel
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `privilege` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('Junxiaoniao', 'fh789524560', 'junxiaoniao@gmail', 'admin');
INSERT INTO `user` VALUES ('kamigame', 'toma10', 'sword-of-logos@gmail.com', 'Normal User');

SET FOREIGN_KEY_CHECKS = 1;
