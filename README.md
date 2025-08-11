# 个人博客后端项目

## 项目简介

这是一个基于Spring Boot 3.x的个人博客后端API服务，采用JDK 17开发，为前端博客项目提供完整的后端支持。

## 技术栈

- **框架**: Spring Boot 3.2.x
- **语言**: Java 17
- **数据库**: MySQL 8.0
- **缓存**: Redis 6.x
- **认证**: Spring Security + JWT
- **文档**: OpenAPI 3.0 (Swagger)
- **构建工具**: Maven
- **其他**: Spring Data JPA, Lombok, MapStruct

## 功能特性

### 核心功能
- ✅ 文章管理（CRUD、分页、搜索）
- ✅ 分类管理
- ✅ 标签管理
- ✅ 用户认证（JWT）
- ✅ 评论系统
- ✅ 访客统计
- ✅ 天气信息集成

### 技术特性
- ✅ RESTful API设计
- ✅ 统一异常处理
- ✅ 参数验证
- ✅ 数据库连接池
- ✅ Redis缓存
- ✅ Swagger API文档
- ✅ 日志记录

## 快速开始

### 环境要求

- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+

### 数据库配置

1. 创建MySQL数据库：
```sql
CREATE DATABASE blog CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 配置数据库连接（application.yml）：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 运行项目

1. 克隆项目：
```bash
git clone https://github.com/your-username/blog-backend.git
cd blog-backend
```

2. 编译项目：
```bash
mvn clean compile
```

3. 运行项目：
```bash
mvn spring-boot:run
```

4. 访问API文档：
```
http://localhost:8080/api/swagger-ui.html
```

## API接口

### 文章相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/articles` | 获取文章列表 |
| GET | `/api/articles/{id}` | 获取文章详情 |
| POST | `/api/articles` | 创建文章 |
| PUT | `/api/articles/{id}/view` | 更新文章访问量 |
| GET | `/api/articles/category/{categoryId}` | 根据分类获取文章 |
| GET | `/api/articles/tag/{tagId}` | 根据标签获取文章 |
| GET | `/api/articles/search` | 搜索文章 |
| GET | `/api/articles/popular` | 获取热门文章 |

### 分类相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/categories` | 获取所有分类 |
| GET | `/api/categories/{id}` | 根据ID获取分类 |

### 标签相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/tags` | 获取所有标签 |
| GET | `/api/tags/{id}` | 根据ID获取标签 |

## 项目结构

```
src/main/java/com/blog/
├── BlogApplication.java          # 主应用类
├── config/                       # 配置类
├── controller/                   # 控制器层
│   ├── ArticleController.java    # 文章控制器
│   ├── CategoryController.java   # 分类控制器
│   └── TagController.java        # 标签控制器
├── service/                      # 服务层
│   ├── ArticleService.java       # 文章服务接口
│   └── impl/                     # 服务实现
│       └── ArticleServiceImpl.java
├── repository/                   # 数据访问层
│   ├── ArticleRepository.java    # 文章Repository
│   ├── CategoryRepository.java   # 分类Repository
│   └── TagRepository.java        # 标签Repository
├── entity/                       # 实体类
│   ├── BaseEntity.java           # 基础实体
│   ├── Article.java              # 文章实体
│   ├── Category.java             # 分类实体
│   ├── Tag.java                  # 标签实体
│   ├── User.java                 # 用户实体
│   ├── Comment.java              # 评论实体
│   └── VisitorStats.java         # 访客统计实体
├── dto/                          # 数据传输对象
│   ├── ApiResponse.java          # API响应
│   ├── ArticleDto.java           # 文章DTO
│   ├── CategoryDto.java          # 分类DTO
│   ├── TagDto.java               # 标签DTO
│   └── CreateArticleRequest.java # 创建文章请求
├── mapper/                       # 对象映射器
│   ├── ArticleMapper.java        # 文章映射器
│   ├── CategoryMapper.java       # 分类映射器
│   └── TagMapper.java            # 标签映射器
├── enums/                        # 枚举类
│   ├── ArticleStatus.java        # 文章状态
│   ├── UserRole.java             # 用户角色
│   └── CommentStatus.java        # 评论状态
├── exception/                    # 异常处理
│   ├── ResourceNotFoundException.java # 资源未找到异常
│   └── GlobalExceptionHandler.java    # 全局异常处理器
├── security/                     # 安全相关
├── util/                         # 工具类
└── constant/                     # 常量类
```

## 配置说明

### 环境变量

| 变量名 | 说明 | 默认值 |
|--------|------|--------|
| DB_USERNAME | 数据库用户名 | root |
| DB_PASSWORD | 数据库密码 | password |
| REDIS_HOST | Redis主机 | localhost |
| REDIS_PORT | Redis端口 | 6379 |
| REDIS_PASSWORD | Redis密码 | 空 |
| JWT_SECRET | JWT密钥 | your-secret-key |
| GITHUB_CLIENT_ID | GitHub OAuth客户端ID | 空 |
| GITHUB_CLIENT_SECRET | GitHub OAuth客户端密钥 | 空 |
| WEATHER_API_KEY | 天气API密钥 | 空 |

### 配置文件

主要配置文件：`src/main/resources/application.yml`

## 开发指南

### 添加新的实体

1. 在`entity`包下创建实体类
2. 在`repository`包下创建Repository接口
3. 在`dto`包下创建DTO类
4. 在`mapper`包下创建映射器
5. 在`service`包下创建服务接口和实现
6. 在`controller`包下创建控制器

### 代码规范

- 遵循阿里巴巴Java开发手册
- 使用Lombok减少样板代码
- 统一的命名规范
- 完善的注释文档

## 部署

### Docker部署

1. 构建镜像：
```bash
mvn clean package
docker build -t blog-backend .
```

2. 运行容器：
```bash
docker run -d -p 8080:8080 --name blog-backend blog-backend
```

### 传统部署

1. 打包：
```bash
mvn clean package
```

2. 运行：
```bash
java -jar target/blog-backend-1.0.0.jar
```

## 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 联系方式

- 项目地址：[https://github.com/your-username/blog-backend](https://github.com/your-username/blog-backend)
- 问题反馈：[Issues](https://github.com/your-username/blog-backend/issues)
