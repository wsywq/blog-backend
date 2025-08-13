# 博客后端 CI/CD 技术方案

本方案面向当前仓库（Spring Boot 3 + Java 17 + Maven + Docker），目标是：
- 在 PR 和主干分支上执行快速、稳定的 CI（编译、测试、制品产出与镜像构建校验）。
- 在打 tag 或手动触发时进行 CD，将镜像推送到 GHCR，并为后续环境部署提供清晰的落地路径。

## 一、流水线总览

- CI：`.github/workflows/ci.yml`
  - 触发：`push` 到 `main/develop/feature/*`，或对 `main/develop` 发起 `pull_request`。
  - 任务：Maven 缓存、编译、测试、上传 JAR 制品、构建 Docker 镜像（不推送）。
- CD：`.github/workflows/cd.yml`
  - 触发：匹配 `v*.*.*` 的 tag，或手动 `workflow_dispatch`。
  - 任务：登录 GHCR，构建并推送镜像，产出镜像标签信息供后续部署使用。

## 二、构建与测试

- Java 发行版：Temurin 17（actions/setup-java@v4）
- Maven 命令：`mvn -B -ntp clean verify`
  - `-B` 批处理模式提升稳定性
  - `-ntp` 禁用传输进度日志减少噪音
- 缓存：使用 `setup-java` 的 `cache: maven`，自动缓存依赖
- 制品上传：`actions/upload-artifact@v4` 上传 `target/*.jar`

## 三、Docker 镜像策略

- 构建：`docker/build-push-action@v5`
- CI 阶段只构建不推送，校验 Dockerfile 可用性，减少注册表污染
- CD 阶段登录 GHCR 并推送，镜像命名：`ghcr.io/<owner>/<repo>:<tag|sha>`
- 元数据：`docker/metadata-action@v5` 自动生成 multi-tags 与 labels
- `.dockerignore` 减少构建上下文：忽略 `target/`、`.git/`、IDE 文件等

## 四、版本与发布

- 语义化版本：`vMAJOR.MINOR.PATCH`（例如 `v1.2.3`）
- 打 tag 触发 CD，自动构建并推送相应 tag 的镜像
- 可通过 `workflow_dispatch` 输入 `image_tag` 进行自定义推送

## 五、环境与部署建议

- 注册表：GitHub Container Registry（GHCR）
- 环境变量与机密：
  - 构建阶段使用 `GITHUB_TOKEN` 登录 GHCR（无需额外配置）
  - 运行阶段敏感信息（数据库、Redis、JWT 等）通过部署环境的密钥管理注入（例如 K8s Secret、Compose env_file、Cloud Secret Manager）
- 部署形态示例：
  - Docker Compose：更新 `image: ghcr.io/<owner>/<repo>:<tag>`，移除本地 build，注入 `.env`
  - Kubernetes：
    - 使用 Deployment 滚动更新
    - 通过 Secret 与 ConfigMap 管理敏感配置与非敏感配置
    - 使用 HPA/PodDisruptionBudget/Readiness/Liveness 探针

## 六、质量门禁与扩展

- 可选门禁：
  - 最低测试覆盖率（结合 `jacoco-maven-plugin`）
  - Static Code Analysis（`spotless`/`checkstyle`/`spotbugs`）
  - SCA/容器扫描（`aquasecurity/trivy-action`）
- 并行与缓存：
  - 使用 `actions/cache`（如构建 Docker 层 cache-from GHCR）
- 短板与改进：
  - 当前 Dockerfile 在构建阶段安装 Maven，建议改为多阶段构建（builder 镜像 + 运行时镜像）以减少体积与时间

## 七、目录与文件

- `.github/workflows/ci.yml`：CI 工作流
- `.github/workflows/cd.yml`：CD 工作流
- `.dockerignore`：构建上下文忽略
- 本文档：`docs/CI-CD-方案.md`

## 八、落地步骤

1) 合并本 PR，启用 CI
2) 在仓库 `Settings > Actions > General` 确保允许 GitHub Actions 运行
3) 推送 tag：`git tag v1.0.0 && git push origin v1.0.0` 触发 CD
4) 部署侧拉取 `ghcr.io/<owner>/<repo>:v1.0.0` 进行上线

## 九、后续路线

- 引入 Jacoco 覆盖率门禁
- 引入 Spotless/Checkstyle 统一风格
- 引入 Trivy 容器与依赖漏洞扫描
- 重构 Dockerfile 为多阶段构建，支持 build cache