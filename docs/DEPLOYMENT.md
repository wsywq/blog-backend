# 部署指南

## 1. 使用 Docker Compose

示例 `docker-compose.yml` 更新为使用远程镜像（替换 <owner>/<repo> 与 <tag>）：

```yaml
depends_on:
  - mysql
  - redis
services:
  blog-app:
    image: ghcr.io/<owner>/<repo>:<tag>
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/blog_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
      - SPRING_DATASOURCE_USERNAME=blog_user
      - SPRING_DATASOURCE_PASSWORD=blog_password
      - SPRING_REDIS_HOST=redis
      - SPRING_REDIS_PORT=6379
      - JWT_SECRET=${JWT_SECRET}
    ports:
      - "8080:8080"
```

建议将敏感信息放入 `.env` 文件或外部 Secret 管理系统。

## 2. 使用 Kubernetes

- 创建 Secret 与 ConfigMap 提供数据库/Redis/JWT 配置
- 部署 Deployment 与 Service，示例容器镜像：

```yaml
containers:
  - name: blog-backend
    image: ghcr.io/<owner>/<repo>:<tag>
    envFrom:
      - secretRef:
          name: blog-backend-secrets
      - configMapRef:
          name: blog-backend-config
    ports:
      - containerPort: 8080
    readinessProbe:
      httpGet:
        path: /actuator/health
        port: 8080
    livenessProbe:
      httpGet:
        path: /actuator/health
        port: 8080
```

按需配置 HPA、PDB 与 Ingress。