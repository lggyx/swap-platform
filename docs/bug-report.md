# Bug 报告

> 生成日期：2025-01-15  
> 扫描范围：`swap-platform-admin` 后端 + `swap-platform-ui` 前端  
> 扫描方式：代码审查（Code Review）

---

## 🔴 严重 Bug

| # | 文件 | 行号 | 问题描述 | 影响 |
|---|------|------|---------|------|
| 1 | `swap-platform-server/.../UserServiceImpl.java` | 84 | 登录成功后硬编码设置 `role = "user"`，导致 Seller / Admin 登录后 token 角色均为 user | 角色校验混乱，卖家/管理员无法访问应有接口 |
| 2 | `swap-platform-server/.../UserServiceImpl.java` | 92, 103, 135 | `getProfile` / `updateProfile` / `updatePassword` 中硬编码 `substring(7)` 截取用户名，但 user 的 token 前缀为 `user___`（6 字符），实际截取位置错误 | 查询不到当前用户，更新/改密均失败 |
| 3 | `swap-platform-server/.../ItemInfoServiceImpl.java` | 72 | 分页查询构建 `PageResult` 时，`total` 字段使用 `itemInfoList.size()`（当前页记录数）而非 `itemInfoPageResult.getTotal()`（总记录数） | 前端分页组件无法正确判断总页数，导致翻页功能异常 |
| 4 | 多个 Controller | 全文件 | `ItemInfoController`、`ItemCommentsController`、`ExchangeDealsTestController`、`FavoritesController`、`ItemCategoriesController`、`AnnouncementsTestController` 均为空壳，仅有 `@RequestMapping`，无任何接口方法定义 | 前端调用 `/api/item-info/list`、`/api/item-comments`、`/api/favorites` 等会直接 404 |
| 5 | `swap-platform-admin/.../SwapPlatformApplication.java` | 12 | 调用 `Dotenv.configure().directory("..")` 指向父目录，但实际 `.env` 文件位于 `swap-platform-admin/` 项目根目录下 | 环境变量加载失败，启动时数据库/Redis 等配置缺失，程序无法正常运行 |
| 6 | `swap-platform-admin/.../JwtFilter.java` | 18-33 | 白名单包含 `/user`（获取全部用户列表）、`/api/config/banners` 等敏感路径，未授权用户可直接访问 | 敏感信息泄露风险 |

---

## 🟡 中等问题

| # | 文件 | 行号 | 问题描述 |
|---|------|------|---------|
| 7 | `swap-platform-admin/.../CorsConfig.java` | 15 | CORS 白名单 `http://10.162.137.1:8913/` 带有尾部斜杠，实际请求 origin 不包含尾部 `/`，导致跨域失败 |
| 8 | `swap-platform-admin/pom.xml` | 8 | 父依赖版本使用 `3.5.9-SNAPSHOT` 快照版本，不稳定且可能需要访问内部仓库才能下载 | 项目构建依赖不稳定，新人 clone 后可能无法正常编译 |
| 9 | `swap-platform-ui/src/main.ts` | 相关段 | 路由配置直接写在 `main.ts` 中，`router/index.ts` 为空文件，结构不规范 | 后续维护困难 |
| 10 | `swap-platform-ui/src/App.vue` | 15 | 导航栏包含"调试"入口，未做环境判断，生产环境仍会显示 | 生产环境暴露测试入口 |
| 11 | `swap-platform-ui/src/api/request.ts` | 29 | 响应拦截器同时兼容 `code=1` 和 `code=200`，但后端统一返回 `code=1`，兼容逻辑冗余 | 代码可读性下降 |

---

## 🟢 轻微问题 / 优化建议

| # | 文件 | 行号 | 问题描述 |
|---|------|------|---------|
| 12 | `swap-platform-ui/src/views/ProfileView.vue` | 8-15 | `profile` 响应式变量初始值缺少 `id`、`addTime` 等字段，可能出现 `undefined` 渲染异常 |
| 13 | `swap-platform-admin/.../AdminUserController.java` | 22-23 | 重复导入 `com.lggyx.vo.ConfigVO`，编译阶段产生警告 |
| 14 | `swap-platform-admin/.../JwtUtil.java` | 62-68 | 定义了 `deleteToken()` 方法，但 JWT 为无状态 token，服务端无法真正删除，该方法无实际作用 |

---

## 修复优先级建议

1. **P0（立即修复）**：Bug #1 ~ #5 —— 涉及核心功能不可用
2. **P1（尽快修复）**：Bug #6 ~ #8 —— 涉及安全、跨域、构建稳定性
3. **P2（迭代修复）**：Bug #9 ~ #11 —— 代码规范、用户体验
4. **P3（可选优化）**：Bug #12 ~ #14 —— 轻微问题

---

## 关联文件索引

| 路径 | 说明 |
|------|------|
| `swap-platform-server/src/main/java/com/lggyx/service/impl/UserServiceImpl.java` | 用户服务（Seller/User 登录逻辑） |
| `swap-platform-server/src/main/java/com/lggyx/service/impl/ItemInfoServiceImpl.java` | 旧物信息服务（分页逻辑） |
| `swap-platform-server/src/main/java/com/lggyx/controller/ItemInfoController.java` | 旧物信息控制器（空壳） |
| `swap-platform-server/src/main/java/com/lggyx/controller/ItemCommentsController.java` | 评论控制器（空壳） |
| `swap-platform-admin/src/main/java/com/lggyx/config/JwtFilter.java` | JWT 白名单过滤 |
| `swap-platform-admin/src/main/java/com/lggyx/config/CorsConfig.java` | 跨域配置 |
| `swap-platform-admin/src/main/java/com/lggyx/SwapPlatformApplication.java` | 启动类（Dotenv 加载） |
| `swap-platform-ui/src/api/request.ts` | 请求拦截器 |
| `swap-platform-ui/src/views/ProfileView.vue` | 个人中心页 |
