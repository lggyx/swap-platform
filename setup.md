# Swap Platform 启动指南

## 环境要求

- JDK 17
- Maven 3.9+（项目已包含 Maven Wrapper，无需单独安装）
- Node.js 18+ / pnpm / bun（推荐 Node.js 20+）
- MySQL 8.0+
- Git（可选，用于克隆仓库）

## 快速启动

### 方式一：一键脚本（推荐）

在项目根目录双击运行：

```bat
quick-start.bat
```

脚本会自动完成：初始化数据库 → 启动后端 → 安装前端依赖 → 启动前端。

### 方式二：手动启动

#### 1. 初始化数据库

```bat
mysql -u root -p < docs\db\swap-platform.sql
```

#### 2. 启动后端

```bat
cd C:\Users\15853\Workspace\swap-platform-final
mvnw.cmd spring-boot:run
```

后端默认启动在：http://localhost:8912
接口文档（Knife4j）：http://localhost:8912/doc.html

#### 3. 启动前端

打开新的终端窗口：

```bat
cd C:\Users\15853\Workspace\swap-platform-final\swap-platform-ui
npm.cmd install
npm.cmd run dev
```

前端默认启动在：http://localhost:5173

## 配置说明

### 环境变量

后端使用环境变量读取数据库和 Knife4j 配置，在 `swap-platform-admin` 根目录创建 `.env` 文件：

```env
MYSQL_DATABASE=swap_platform
MYSQL_USER=root
MYSQL_PASSWORD=你的密码
KNIFE4J_USER=admin
KNIFE4J_PASSWORD=admin123
```

> 项目根目录已提供 `.env.example` 模板，复制后修改即可。

### 前端代理

如需修改后端地址，编辑 `swap-platform-ui\src\api\request.ts`：

```ts
const BASE_URL = 'http://localhost:8912'
```

## 常见问题

1. **端口占用**：如果 8912 或 5173 被占用，修改 `application.yml` 中的 `server.port` 或前端 `vite.config.ts`。
2. **数据库连接失败**：检查 MySQL 服务是否启动，用户名密码是否正确。
3. **Maven 依赖下载慢**：建议配置国内镜像源（阿里云、华为云）。

## 目录说明

```
swap-platform-final/
├── swap-platform-admin/       # 后台管理（含启动类）
├── swap-platform-server/      # 业务服务层
├── swap-platform-common/      # 公共组件（拦截器、工具类）
├── swap-platform-pojo/        # 实体类 / DTO / VO
├── swap-platform-ui/          # 前端 Vue3 项目
├── docs/db/
│   └── swap-platform.sql       # 数据库初始化脚本
├── swap-skills/               # Agent Skills 定义
├── trace/                     # 测试轨迹记录
└── quick-start.bat            # 一键启动脚本
```

## 运行测试

项目包含自动生成的联调测试脚本，前端目录下：

```bat
cd swap-platform-ui
node scripts/api-test-auto.js
```

测试报告将输出到 `swap-platform-ui/scripts/` 目录。
