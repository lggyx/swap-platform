-- 删除数据库
DROP DATABASE IF EXISTS `swap-platform`;
-- 创建数据库
CREATE DATABASE IF NOT EXISTS `swap-platform`
    DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `swap-platform`;

-- 1. 系统配置表
CREATE TABLE `config` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `name` varchar(100) NOT NULL COMMENT '配置参数名称',
                          `value` varchar(100) DEFAULT NULL COMMENT '配置参数值',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='系统配置';

INSERT INTO `config` VALUES
                         (1,'banner1','http://localhost:8080/swap_platform/upload/banner1.png'),
                         (2,'banner2','http://localhost:8080/swap_platform/upload/banner2.jpg'),
                         (3,'banner3','http://localhost:8080/swap_platform/upload/banner3.jpg'),
                         (6,'homepage',NULL);

-- 2. 旧物类型表
CREATE TABLE `item_categories` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `category_name` varchar(200) DEFAULT NULL COMMENT '类型名称',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1614756652241 DEFAULT CHARSET=utf8 COMMENT='旧物类型';

INSERT INTO `item_categories` VALUES
                                  (31,'2025-03-03 07:22:20','图书'),
                                  (1614756385820,'2025-03-03 07:26:25','衣服'),
                                  (1614756639180,'2025-03-03 07:30:39','手机'),
                                  (1614756645515,'2025-03-03 07:30:45','鞋子'),
                                  (1614756652240,'2025-03-03 07:30:51','箱包');

-- 3. 卖家表
CREATE TABLE `seller` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `seller_name` varchar(200) NOT NULL COMMENT '卖家用户名',
                          `password` varchar(200) NOT NULL COMMENT '密码',
                          `real_name` varchar(200) NOT NULL COMMENT '真实姓名',
                          `gender` varchar(200) DEFAULT NULL COMMENT '性别',
                          `phone` varchar(200) DEFAULT NULL COMMENT '手机号',
                          `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
                          `photo` varchar(200) DEFAULT NULL COMMENT '头像URL',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `uniq_seller_name` (`seller_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1614756609738 DEFAULT CHARSET=utf8 COMMENT='卖家信息';

INSERT INTO `seller` VALUES
    (1614756609737,'2025-03-03 07:30:09','seller01','123456','李伟','女','13800000000','liwei@qq.com','http://localhost:8080/swap_platform/upload/liwei.jpg');

-- 4. 用户表
CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `username` varchar(200) NOT NULL COMMENT '用户名',
                        `password` varchar(200) NOT NULL COMMENT '密码',
                        `real_name` varchar(200) NOT NULL COMMENT '真实姓名',
                        `gender` varchar(200) DEFAULT NULL COMMENT '性别',
                        `phone` varchar(200) DEFAULT NULL COMMENT '手机号',
                        `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
                        `photo` varchar(200) DEFAULT NULL COMMENT '头像URL',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uniq_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1614756771095 DEFAULT CHARSET=utf8 COMMENT='平台用户';

INSERT INTO `user` VALUES
    (1614756771094,'2025-03-03 07:32:51','user01','123456','王芳','女','13900000000','wangfang@qq.com','http://localhost:8080/swap_platform/upload/wangfang.jpg');

-- 5. 旧物信息表
CREATE TABLE `item_info` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `seller_name` varchar(200) DEFAULT NULL COMMENT '卖家名',
                             `contact_name` varchar(200) DEFAULT NULL COMMENT '联系人姓名',
                             `contact_phone` varchar(200) DEFAULT NULL COMMENT '联系人手机',
                             `item_name` varchar(200) NOT NULL COMMENT '旧物名称',
                             `item_category` varchar(200) NOT NULL COMMENT '旧物类型',
                             `item_image` varchar(200) DEFAULT NULL COMMENT '旧物图片URL',
                             `exchange_request` longtext COMMENT '置换需求',
                             `item_detail` longtext COMMENT '旧物详情',
                             `like_count` int(11) DEFAULT '0' COMMENT '点赞数',
                             `dislike_count` int(11) DEFAULT '0' COMMENT '点踩数',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1614756747287 DEFAULT CHARSET=utf8 COMMENT='旧物信息';

INSERT INTO `item_info` VALUES
                            (1614756710816,'2025-03-03 07:31:50','seller01','李伟','13800000000','粉色运动鞋','鞋子','http://localhost:8080/swap_platform/upload/pink_shoes.jpg','想换一双黑色运动鞋','<p>九成新，Size 38</p>',0,0),
                            (1614756747286,'2025-03-03 07:32:26','seller01','李伟','13800000000','华为P30','手机','http://localhost:8080/swap_platform/upload/huawei_phone.jpg','可换小米或三星同级机型','<p>128G，完好无磕碰</p>',2,0);

-- 6. 旧物评论表
CREATE TABLE `item_comments` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `item_id` bigint(20) NOT NULL COMMENT '旧物ID',
                                 `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                 `content` longtext NOT NULL COMMENT '评论内容',
                                 `reply` longtext COMMENT '回复内容',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1614756859735 DEFAULT CHARSET=utf8 COMMENT='旧物评论';

INSERT INTO `item_comments` VALUES
                                (81,'2025-03-03 07:22:20',1,1,'看起来不错！','谢谢夸奖~'),
                                (82,'2025-03-03 07:22:20',2,2,'还能再便宜吗？','私聊详谈'),
                                (1614756859734,'2025-03-03 07:34:19',1614756747286,1614756771094,'想用小米11换，可以吗？',NULL);

-- 7. 收藏表
CREATE TABLE `favorites` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                             `item_id` bigint(20) DEFAULT NULL COMMENT '旧物ID',
                             `item_type` varchar(200) DEFAULT NULL COMMENT '旧物类型',
                             `name` varchar(200) NOT NULL COMMENT '收藏名称',
                             `image` varchar(200) NOT NULL COMMENT '收藏图片URL',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1614756851015 DEFAULT CHARSET=utf8 COMMENT='用户收藏';

INSERT INTO `favorites` VALUES
    (1614756851014,'2025-03-03 07:34:10',1614756771094,1614756747286,'手机','华为P30','http://localhost:8080/swap_platform/upload/huawei_phone.jpg');

-- 8. 公告表
CREATE TABLE `announcements` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `title` varchar(200) NOT NULL COMMENT '公告标题',
                                 `introduction` longtext COMMENT '简介',
                                 `image` varchar(200) NOT NULL COMMENT '公告图片URL',
                                 `content` longtext NOT NULL COMMENT '公告内容',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1614756590324 DEFAULT CHARSET=utf8 COMMENT='平台公告';

INSERT INTO `announcements` VALUES
                                (71,'2025-03-03 07:22:20','欢迎光临闲置交换平台','一个值得信赖的旧物交换社区','http://localhost:8080/swap_platform/upload/welcome.jpg','<p>在这里，你可以自由发布、浏览、交换闲置物品。</p>'),
                                (1614756590323,'2025-03-03 07:29:49','新功能上线','现已支持多张图片上传','http://localhost:8080/swap_platform/upload/feature.jpg','<p>发布旧物时，可一次性上传多张图片，让物品展示更全面。</p>');

-- 9. 置换交易表
CREATE TABLE `exchange_deals` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `username` varchar(200) DEFAULT NULL COMMENT '用户名',
                                  `real_name` varchar(200) DEFAULT NULL COMMENT '真实姓名',
                                  `phone` varchar(200) DEFAULT NULL COMMENT '手机号',
                                  `target_item_name` varchar(200) DEFAULT NULL COMMENT '目标旧物名称',
                                  `target_item_image` varchar(200) DEFAULT NULL COMMENT '目标旧物图片',
                                  `offer_item_name` varchar(200) DEFAULT NULL COMMENT '置换物名称',
                                  `offer_item_image` varchar(200) DEFAULT NULL COMMENT '置换物图片',
                                  `offer_detail` longtext COMMENT '置换物详情',
                                  `approved` varchar(200) DEFAULT '否' COMMENT '是否审核',
                                  `approval_reply` longtext COMMENT '审核回复',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1614756898632 DEFAULT CHARSET=utf8 COMMENT='置换交易';

INSERT INTO `exchange_deals` VALUES
    (1614756898631,'2025-03-03 07:34:58','user01','王芳','13900000000','华为P30','http://localhost:8080/swap_platform/upload/huawei_phone.jpg','小米11','http://localhost:8080/swap_platform/upload/xiaomi_phone.jpg','<p>小米11，128G，成色99新</p>','是','同意置换，请尽快联系对方完成交易。');

-- 10. 管理员表
CREATE TABLE `admin_user` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `username` varchar(100) NOT NULL COMMENT '用户名',
                              `password` varchar(100) NOT NULL COMMENT '密码',
                              `role` varchar(100) DEFAULT '管理员' COMMENT '角色',
                              `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台管理员';

INSERT INTO `admin_user` VALUES
    (1,'admin','admin123','管理员','2025-03-03 07:22:20');

-- 11. 用户令牌表
CREATE TABLE `user_token` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                              `username` varchar(100) NOT NULL COMMENT '用户名',
                              `table_name` varchar(100) DEFAULT NULL COMMENT '所属表名',
                              `role` varchar(100) DEFAULT NULL COMMENT '角色',
                              `token` varchar(200) NOT NULL COMMENT '令牌',
                              `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `expire_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户登录令牌';

INSERT INTO `user_token` VALUES
                             (1,11,'用户1','user','用户','q9wuirj0zh11ecrymgletpm1kghum3at','2025-03-03 07:24:24','2025-03-03 00:24:25'),
                             (2,1,'admin','admin_user','管理员','zuuudnq8i9cqnxiiyya67qvwp8j38gwc','2025-03-03 07:25:28','2025-03-03 00:36:30'),
                             (3,1614756609737,'seller01','seller','卖家','ez616poah35ptp54l1t8o1ccwmbhv6hj','2025-03-03 07:30:14','2025-03-03 00:35:19'),
                             (4,1614756771094,'user01','user','用户','fasbtyj61yfp1t3ecbce3i51yo5ux5qf','2025-03-03 07:32:56','2025-03-03 00:37:34');