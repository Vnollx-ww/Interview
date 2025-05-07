# 🛒 电商基础功能平台（Spring Boot + MySQL + Redis）

基于 Spring Boot 实现的电商核心功能系统，包含用户登录注册、商品点赞、购物车管理等模块，采用 MySQL 存储业务数据，Redis 优化点赞计数性能，适合学习分布式缓存与前后端交互。


## 🌟 技术栈
| 技术选型         | 版本     | 说明                          |
|--------------|--------|-------------------------------|
| **Spring Boot** | 2.7.7  | 快速构建 Java 后端服务         |
| **MySQL**    | 8.0.32 | 关系型数据库，存储用户/商品数据 |
| **Redis**    | 7.0.11 | 缓存点赞计数，提升高并发性能   |
| **MyBatis**  | 1.3.0  | 简化数据库操作，支持代码生成   |


## 🚀 核心功能
### 1. 用户模块
- ✅ **注册登录**：支持账号密码注册（密码加盐 + BCrypt 加密），登录返回 JWT Token 用于身份验证，用户Id通过雪花算法生成
- ✅ **安全机制**：禁止明文存储密码，注册时自动生成盐值（`salt`）与密码混合加密

### 2. 商品点赞
- ✅ **原子操作**：使用 Redis `INCRBY` 实现点赞/取消点赞的原子计数，避免并发问题
- ✅ **数据同步**：定时任务将 Redis 点赞数据同步到 MySQL，保证最终一致性
- ✅ **唯一约束**：用户对同一商品最多点赞一次，重复点击自动取消

### 3. 购物车模块
- ✅ **商品管理**：支持修改数量、删除条目，实时记录加入时的商品价格
- ✅ **唯一约束**：同一用户同一商品在购物车中仅存一条记录（通过 `(user_id, product_id)` 唯一索引）
- ✅ **批量操作**：支持一次性查询购物车所有商品及对应详情


## 📦 环境准备
### 1. 必备工具
- 🛠 **Java**：JDK 11+（[下载](https://www.oracle.com/java/technologies/downloads/)）
- 📦 **Maven**：3.8.8+（[下载](https://maven.apache.org/download.cgi)）
- 🏢 **MySQL**：8.0+（[下载](https://dev.mysql.com/downloads/)）
- 🐳 **Redis**：6.0+（[下载](https://redis.io/download/)，推荐使用 Docker 部署）
- 🧰 **IDE**：IntelliJ IDEA（推荐）或 Eclipse

# Interview 项目文档

## 🚀 快速启动指南

### 1. 克隆项目仓库# 克隆代码仓库到本地
git clone https://github.com/Vnollx-ww/Interview.git

# 进入项目根目录
cd interview
---

### 2. 配置环境参数
**配置文件路径**：`src/main/resources/application.yml`

需根据实际环境修改以下核心配置项：
- **数据库连接**（如 MySQL）：配置数据库地址、端口、用户名及密码
- **Redis 服务**：配置 Redis 服务器地址、端口及认证密码

### 3. 启动项目服务

#### 3.1 通过 IDE 启动（推荐开发者）
1. 打开 IntelliJ IDEA，选择 `File > Open` 导入项目
2. 等待 IDE 自动加载依赖（Maven 项目会自动下载依赖包）
3. 在项目结构中找到启动类：`InterviewApplication.java`（通常位于 `src/main/java/[根包路径]` 下）
4. 点击启动按钮 🚀（或使用快捷键 `Shift + F10`）启动服务

#### 3.2 通过 JAR 包启动 # 第一步：打包项目（首次启动前执行，生成可执行 JAR）
mvn clean package 

# 第二步：启动服务
java -jar target/interview.jar

### ✅ 验证启动成功
服务启动后，控制台会输出以下关键日志：

INFO 9944 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''

INFO 9944 --- [           main] c.e.interview.InterviewApplication       : Started InterviewApplication in 5.351 seconds (JVM running for 7.198) 

''此时可通过 `http://localhost:8080` 访问项目接口。
    