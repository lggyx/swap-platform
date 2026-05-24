-- ============================================================
-- swap-platform  Mock 数据补充脚本
-- 用法: 在已有数据库上执行此脚本（不要DROP DATABASE）
-- ============================================================
USE `swap-platform`;

-- ==================== 1. 补充分类 ====================
INSERT INTO `item_categories` (`category_name`) VALUES
('数码电器'),
('家居生活'),
('运动户外'),
('图书音像'),
('母婴用品'),
('美妆护肤'),
('食品饮料'),
('办公用品'),
('乐器'),
('绿植花卉')
ON DUPLICATE KEY UPDATE `category_name` = `category_name`;

-- ==================== 2. 补充用户 ====================
INSERT INTO `user` (`username`, `password`, `real_name`, `gender`, `phone`, `email`, `photo`) VALUES
('zhangsan', '123456', '张三', '男', '13800001001', 'zhangsan@qq.com', 'http://localhost:8080/swap_platform/upload/avatar1.jpg'),
('lisi',      '123456', '李四', '女', '13800001002', 'lisi@qq.com',     'http://localhost:8080/swap_platform/upload/avatar2.jpg'),
('wangwu',    '123456', '王五', '男', '13800001003', 'wangwu@qq.com',   'http://localhost:8080/swap_platform/upload/avatar3.jpg'),
('zhaoliu',   '123456', '赵六', '女', '13800001004', 'zhaoliu@qq.com',  'http://localhost:8080/swap_platform/upload/avatar4.jpg'),
('sunqi',     '123456', '孙七', '男', '13800001005', 'sunqi@qq.com',    'http://localhost:8080/swap_platform/upload/avatar5.jpg')
ON DUPLICATE KEY UPDATE `username` = VALUES(`username`);

-- ==================== 3. 补充卖家 ====================
INSERT INTO `seller` (`seller_name`, `password`, `real_name`, `gender`, `phone`, `email`, `photo`) VALUES
('seller02', '123456', '陈卖家', '男', '13900002001', 'seller02@qq.com', 'http://localhost:8080/swap_platform/upload/seller2.jpg'),
('seller03', '123456', '刘卖家', '女', '13900002002', 'seller03@qq.com', 'http://localhost:8080/swap_platform/upload/seller3.jpg'),
('seller04', '123456', '周卖家', '男', '13900002003', 'seller04@qq.com', 'http://localhost:8080/swap_platform/upload/seller4.jpg'),
('seller05', '123456', '吴卖家', '女', '13900002004', 'seller05@qq.com', 'http://localhost:8080/swap_platform/upload/seller5.jpg')
ON DUPLICATE KEY UPDATE `seller_name` = VALUES(`seller_name`);

-- ==================== 4. 补充旧物（共10条） ====================
-- 注意：先查询已存在的分类和卖家ID，用子查询确保关联正确
INSERT INTO `item_info`
(`seller_name`, `contact_name`, `contact_phone`, `item_name`, `item_category`, `item_image`, `exchange_request`, `item_detail`, `like_count`, `dislike_count`)
VALUES
-- id 会自动递增
('seller01', '李伟', '13800000000', 'iPad Pro 11寸', '数码电器',
 'http://localhost:8080/swap_platform/upload/ipad.jpg',
 '可换 AirPods Pro 或同等价位耳机',
 '<p>2024款，256G，WiFi版，贴膜带壳，几乎全新。</p>', 3, 0),

('seller02', '陈卖家', '13900002001', '北欧风台灯', '家居生活',
 'http://localhost:8080/swap_platform/upload/lamp.jpg',
 '想换香薰机或装饰画',
 '<p>实木底座，三档调光，买来没怎么用。</p>', 1, 0),

('seller03', '刘卖家', '13900002002', '山地自行车', '运动户外',
 'http://localhost:8080/swap_platform/upload/bike.jpg',
 '可换滑板或健身器材',
 '<p>捷安特 ATX720，27速，骑行不到500公里，转让价1800，想换同价位物品。</p>', 5, 1),

('seller01', '李伟', '13800000000', '高等数学第七版', '图书音像',
 'http://localhost:8080/swap_platform/upload/math_book.jpg',
 '换同专业教材',
 '<p>同济版高等数学，上册，无笔记无划痕。</p>', 0, 0),

('seller04', '周卖家', '13900002003', '婴儿推车', '母婴用品',
 'http://localhost:8080/swap_platform/upload/stroller.jpg',
 '换婴儿安全座椅',
 '<p>好孩子品牌，可折叠，适合0-3岁，功能完好。</p>', 2, 0),

('seller05', '吴卖家', '13900002004', 'SK-II 神仙水', '美妆护肤',
 'http://localhost:8080/swap_platform/upload/skii.jpg',
 '换同价位面霜',
 '<p>230ml，余量80%，日期新鲜。</p>', 4, 0),

('seller02', '陈卖家', '13900002001', '咖啡机', '食品饮料',
 'http://localhost:8080/swap_platform/upload/coffee.jpg',
 '换茶叶或咖啡豆',
 '<p>德龙半自动咖啡机，打奶泡功能正常。</p>', 1, 1),

('seller03', '刘卖家', '13900002002', '机械键盘', '办公用品',
 'http://localhost:8080/swap_platform/upload/keyboard.jpg',
 '换鼠标或鼠标垫',
 '<p>Cherry轴，红轴，RGB背光，使用一年。</p>', 3, 0),

('seller04', '周卖家', '13900002003', '尤克里里', '乐器',
 'http://localhost:8080/swap_platform/upload/ukulele.jpg',
 '换吉他或口琴',
 '<p>23寸，桃花心木，音色不错，适合初学者。</p>', 0, 0),

('seller05', '吴卖家', '13900002004', '多肉植物盆栽', '绿植花卉',
 'http://localhost:8080/swap_platform/upload/plant.jpg',
 '换其他盆栽',
 '<p>熊童子+桃蛋组合，带陶瓷盆，养护良好。</p>', 2, 0);

-- ==================== 5. 补充旧物评论（关联正确的 item_id） ====================
-- 先查出刚插入的旧物 ID（假设从 3 开始，因为已有2条）
INSERT INTO `item_comments` (`item_id`, `user_id`, `content`, `reply`) VALUES
-- 评论 ID 会自动递增
(3, 1, 'iPad 还在吗？想换我的 AirPods', '在的，私聊详谈'),
(3, 2, '256G 够用吗？平时存很多照片', '完全够，我装了50个App还有150G'),
(4, 1, '台灯有保修吗？', NULL),
(5, 2, '自行车能试骑吗？', '可以，周末在公园见面'),
(5, 3, '成色怎么样？有掉漆吗？', '几乎没有，只骑过几次'),
(6, 1, '书是上册还是上下册？', '只有上册'),
(7, 3, '推车能放平吗？', '可以，靠背有三档调节'),
(7, 4, '原价多少？', '原价1200，现在想换个安全座椅'),
(8, 2, '神仙水生产日期是什么时候？', '2024年8月'),
(9, 1, '咖啡机支持胶囊吗？', '半自动的，需要自己填粉'),
(9, 3, '打奶泡容易吗？', '还行，多练习几次就好');

-- ==================== 6. 补充收藏 ====================
INSERT INTO `favorites` (`user_id`, `item_id`, `item_type`, `name`, `image`) VALUES
(1, 3, '数码电器', 'iPad Pro 11寸', 'http://localhost:8080/swap_platform/upload/ipad.jpg'),
(1, 8, '办公用品', '机械键盘', 'http://localhost:8080/swap_platform/upload/keyboard.jpg'),
(2, 5, '母婴用品', '婴儿推车', 'http://localhost:8080/swap_platform/upload/stroller.jpg'),
(2, 6, '美妆护肤', 'SK-II 神仙水', 'http://localhost:8080/swap_platform/upload/skii.jpg'),
(3, 3, '数码电器', 'iPad Pro 11寸', 'http://localhost:8080/swap_platform/upload/ipad.jpg'),
(3, 9, '食品饮料', '咖啡机', 'http://localhost:8080/swap_platform/upload/coffee.jpg'),
(4, 7, '家居生活', '北欧风台灯', 'http://localhost:8080/swap_platform/upload/lamp.jpg');

-- ==================== 7. 补充公告 ====================
INSERT INTO `announcements` (`title`, `introduction`, `image`, `content`) VALUES
('平台规范更新', '请各位用户遵守交换规则', 'http://localhost:8080/swap_platform/upload/notice1.jpg',
 '<p>为了维护良好的交换环境，请大家诚信交易，及时响应对方的置换请求。</p>'),

('春节活动', '春节期间发布物品享流量扶持', 'http://localhost:8080/swap_platform/upload/notice2.jpg',
 '<p>活动时间：1月15日-2月15日，活动期间发布的物品将获得首页推荐位。</p>'),

('安全提示', '线下交易请注意人身财产安全', 'http://localhost:8080/swap_platform/upload/notice3.jpg',
 '<p>建议在公共场所见面交易，检查物品后再确认置换。</p>');

-- ==================== 8. 补充置换记录 ====================
INSERT INTO `exchange_deals`
(`username`, `real_name`, `phone`, `target_item_name`, `target_item_image`, `offer_item_name`, `offer_item_image`, `offer_detail`, `approved`, `approval_reply`)
VALUES
('zhangsan', '张三', '13800001001', '山地自行车', 'http://localhost:8080/swap_platform/upload/bike.jpg',
 '滑板（沸点系列）', 'http://localhost:8080/swap_platform/upload/skateboard.jpg',
 '<p>滑板8成新，轮子刚换过，适合刷街。</p>', '否', NULL),

('lisi', '李四', '13800001002', '婴儿推车', 'http://localhost:8080/swap_platform/upload/stroller.jpg',
 '婴儿安全座椅（0-4岁）', 'http://localhost:8080/swap_platform/upload/carseat.jpg',
 '<p>安全座椅ISOFIX接口，9成新。</p>', '是', '同意置换，请尽快联系。'),

('wangwu', '王五', '13800001003', '机械键盘', 'http://localhost:8080/swap_platform/upload/keyboard.jpg',
 '罗技G304鼠标', 'http://localhost:8080/swap_platform/upload/mouse.jpg',
 '<p>鼠标99新，换过一次电池。</p>', '否', NULL),

('zhaoliu', '赵六', '13800001004', 'SK-II 神仙水', 'http://localhost:8080/swap_platform/upload/skii.jpg',
 '兰蔻小黑瓶精华50ml', 'http://localhost:8080/swap_platform/upload/lancome.jpg',
 '<p>精华余量充足，日期新鲜。</p>', '是', '已同意，请联系交换。');

-- ==================== 9. 补充点赞/点踩（如有点赞表则插入） ====================
-- 注意：原始SQL中没有独立的点赞表，like_count 在 item_info 里，
-- 这里为了测试方便，如果将来加了 likes 表可以直接用
-- 暂时通过更新 item_info 的 like_count 来模拟已有的点赞
-- 上面的 INSERT 已经设置了初始值，这里无需额外操作

-- ==================== 10. 补充管理员令牌（方便测试） ====================
-- 确保 admin token 未过期（当前时间附近）
INSERT INTO `user_token` (`user_id`, `username`, `table_name`, `role`, `token`, `add_time`, `expire_time`)
VALUES
(1, 'admin', 'admin_user', '管理员', 'admin_test_token_2026', NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY))
ON DUPLICATE KEY UPDATE `token` = VALUES(`token`), `expire_time` = VALUES(`expire_time`);

-- ==================== 完成 ====================
SELECT 'Mock data inserted successfully!' AS result;
