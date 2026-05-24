# 闲置交换平台 (Swap Platform)

一个基于 Spring Boot + Vue 3 的闲置物品交换平台，支持物品发布、置换申请、评论收藏、后台管理等功能。

## 技术栈

### 后端
- Spring Boot 3.x
- MyBatis-Plus
- Knife4j（OpenAPI 3 文档）
- JWT 认证
- MySQL 8.0

### 前端
- Vue 3 + TypeScript
- Vite
- Element Plus
- Axios

## 项目结构

```
swap-platform-final/
├── swap-platform-admin/       # 后台管理模块（含启动类）
├── swap-platform-server/      # 业务服务层
├── swap-platform-common/      # 公共组件（拦截器、工具类、配置）
├── swap-platform-pojo/        # 实体类 / DTO / VO
├── swap-platform-ui/          # 前端 Vue3 项目
├── docs/db/
│   └── swap-platform.sql       # 数据库初始化脚本
├── swap-skills/               # Agent Skills（自动化测试、报告生成）
├── trace/                     # 测试执行轨迹
├── setup.md                   # 启动指南
└── quick-start.bat            # 一键启动脚本
```

## 快速开始

### 环境要求

- JDK 17
- Node.js 18+
- MySQL 8.0+

### 1. 克隆或解压项目

### 2. 初始化数据库

```bat
mysql -u root -p < docs\db\swap-platform.sql
```

### 3. 配置环境变量

在 `swap-platform-admin` 目录创建 `.env` 文件：

```env
MYSQL_DATABASE=swap_platform
MYSQL_USER=root
MYSQL_PASSWORD=你的密码
KNIFE4J_USER=admin
KNIFE4J_PASSWORD=admin123
```

### 4. 启动后端

```bat
mvnw.cmd spring-boot:run
```

访问接口文档：http://localhost:8912/doc.html

### 5. 启动前端

```bat
cd swap-platform-ui
npm.cmd install
npm.cmd run dev
```

访问：http://localhost:5173

## 功能特性

- 🔐 JWT 登录 / 注册（用户 / 卖家双角色）
- 📦 物品发布、浏览、搜索
- 🔄 置换交易申请与审核
- 💬 物品评论与回复
- ⭐ 收藏功能
- 📢 平台公告管理
- 📊 管理员数据统计
- 🛠️ 自动化联调测试（基于 Claude Code Skills）

## 审核材料

本项目为软件工程课程作业 / 技能评测提交，包含：

- `swap-skills/` — 真实可用的 Agent Skills 定义
- `trace/` — 自动化测试的真实执行轨迹
- `scripts/` — 自动生成的测试脚本与报告

## License

MIT
