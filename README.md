# Z-Reader

个人云阅读器！支持 Web 端和安卓端的电子书阅读应用。

## 项目简介

Z-Reader 是一个个人电子书阅读系统，其中有且仅有一位"馆长"（管理员），拥有登录、管理用户/书籍、上传书籍和记录**自己**阅读进度的全部权限。所有其他人都是**纯粹的"游客"**，由管理员注册，只能浏览和阅读，系统**不会**为他们记录任何数据。

## 技术栈

- **后端**: Spring Boot 3.5.7 + Java 21
- **数据库**: MySQL 8.0+
- **ORM**: MyBatis-Plus 3.5.14
- **认证**: Sa-Token 1.44.0
- **文件解析**: Apache Tika 3.2.3
- **构建工具**: Maven

## 项目结构

```
z-reader/
├── z-reader-app/          # 应用启动模块
├── z-reader-auth/         # 认证鉴权模块 ✅ 已完成
├── z-reader-book/         # 书籍管理模块 🚧 待开发
├── z-reader-common/       # 公共工具模块
├── z-reader-progress/     # 阅读进度模块 🚧 待开发
├── z-reader-setting/      # 设置模块 🚧 待开发
├── sql/                   # 数据库脚本
│   ├── table.sql         # 表结构
│   └── init_admin.sql    # 初始化管理员
└── README.md             # 本文件
```

## 快速开始

### 1. 环境要求

- JDK 21+
- Maven 3.8+
- MySQL 8.0+

### 2. 初始化数据库

```bash
# 创建数据库和表
mysql -u root -p < sql/table.sql

# 初始化管理员账号（用户名: admin, 密码: admin123）
mysql -u root -p < sql/init_admin.sql
```

### 3. 配置数据库连接

修改 `z-reader-app/src/main/resources/application-test.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/z_reader?serverTimezone=Asia/Shanghai
    username: root
    password: your_password
```

### 4. 启动应用

```bash
mvn clean package
cd z-reader-app
mvn spring-boot:run
```

应用将在 `http://localhost:8080` 启动。

### 5. 测试 API

使用 `z-reader-auth/test-api.http` 文件测试认证模块 API。

## 模块说明

### ✅ z-reader-auth (认证模块)

提供用户登录、注册游客、用户信息管理等功能。

- **API 文档**: [z-reader-auth/API.md](z-reader-auth/API.md)
- **OpenAPI 规范**: [z-reader-auth/openapi.json](z-reader-auth/openapi.json)

主要接口：
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/register-guest` - 注册游客（需管理员权限）
- `GET /api/auth/current-user` - 获取当前用户信息
- `POST /api/auth/logout` - 用户登出
- `GET /api/auth/check-login` - 检查登录状态

### 🚧 z-reader-book (书籍管理模块)

待开发：书籍上传、编辑、删除、分类管理等功能。

### 🚧 z-reader-progress (阅读进度模块)

待开发：记录管理员的阅读进度、阅读时长等功能。

### 🚧 z-reader-setting (设置模块)

待开发：系统设置、存储配置等功能。

## 数据库设计

- **z_user**: 用户表（管理员 + 游客）
- **z_book**: 书籍信息表
- **z_book_progress**: 阅读进度表（仅管理员）
- **z_book_toc**: 书籍目录表
- **z_book_category**: 书籍分类表
- **z_book_storage**: 书籍文件存储表
- **z_storage_provider**: 存储提供商配置表

详细设计请查看 `sql/table.sql`。

## 开发进度

- [x] 项目架构搭建
- [x] 认证鉴权模块
  - [x] 用户登录
  - [x] 管理员注册游客
  - [x] 用户信息管理
  - [x] Sa-Token 权限控制
  - [x] API 文档（OpenAPI 3.0）
- [ ] 书籍管理模块
- [ ] 阅读进度模块
- [ ] 设置模块
- [ ] Web 前端
- [ ] 安卓客户端

## 贡献指南

欢迎提交 Issue 和 Pull Request！

## 许可证

MIT License

## 联系方式

- 作者: zy
- 邮箱: your-email@example.com
