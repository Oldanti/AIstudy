# AI智能体对话系统

基于Spring Boot和Vue.js的AI智能体对话系统，支持用户注册登录、AI服务配置和智能对话功能。

## 功能特性

- 🔐 用户注册与登录
- ⚙️ AI服务配置（URL、API密钥、模型选择）
- 💬 智能对话功能
- 💾 用户配置持久化存储
- 🎨 现代化响应式UI界面

## 技术栈

- **后端**: Spring Boot 3.5.5, Spring Data JPA, MySQL
- **前端**: Vue.js 3, Axios, 原生CSS
- **数据库**: MySQL 8.0+
- **模板引擎**: Thymeleaf

## 环境要求

- Java 17+
- MySQL 8.0+
- Maven 3.6+

## 安装和运行

### 1. 数据库设置

1. 安装MySQL 8.0+
2. 创建数据库和表：
   ```sql
   -- 执行 database_init.sql 文件中的SQL语句
   mysql -u root -p < database_init.sql
   ```

### 2. 配置数据库连接

编辑 `src/main/resources/application.properties` 文件，修改数据库连接信息：

```properties
# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/aichat?useSSL=false&serverTimezone=UTC&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=your_password_here
```

### 3. 编译和运行

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

### 4. 访问应用

打开浏览器访问：http://localhost:8080

## 使用说明

### 1. 用户注册/登录

- 首次使用需要注册账号
- 支持用户名、邮箱和密码注册
- 登录后可以保存个人AI配置

### 2. 配置AI服务

- 在"服务配置"面板中输入AI服务URL和API密钥
- 点击"获取模型列表"按钮获取可用模型
- 选择要使用的模型并保存配置

### 3. 开始对话

- 配置完成后，在聊天面板中输入问题
- 按回车键或点击"发送"按钮发送消息
- AI会实时回复您的消息

## 测试账号

系统预置了测试账号：
- 用户名: `admin` 密码: `123456`
- 用户名: `test` 密码: `123456`

## API接口

### 认证接口
- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/logout` - 用户退出
- `GET /api/auth/current-user` - 获取当前用户信息

### AI服务接口
- `POST /api/models` - 获取模型列表
- `POST /api/config` - 保存AI配置
- `GET /api/config` - 获取AI配置
- `POST /api/chat` - 发送聊天消息

## 项目结构

```
src/
├── main/
│   ├── java/com/example/aichat/
│   │   ├── controller/     # 控制器
│   │   ├── model/         # 数据模型
│   │   ├── repository/    # 数据访问层
│   │   ├── service/       # 业务逻辑层
│   │   └── AichatApplication.java
│   └── resources/
│       ├── templates/     # 模板文件
│       └── application.properties
└── test/
```

## 注意事项

1. 确保MySQL服务正在运行
2. 修改数据库连接信息后需要重启应用
3. AI服务需要有效的API密钥才能正常工作
4. 建议在生产环境中使用HTTPS和更安全的密码策略

## 故障排除

### 数据库连接失败
- 检查MySQL服务是否运行
- 确认数据库连接信息是否正确
- 检查数据库用户权限

### AI服务调用失败
- 确认API URL格式正确
- 检查API密钥是否有效
- 查看控制台日志获取详细错误信息

## 开发说明

这是一个最小原型demo，主要用于演示基本功能。在生产环境中建议：

1. 添加输入验证和错误处理
2. 实现更安全的认证机制
3. 添加日志记录和监控
4. 优化数据库查询性能
5. 添加单元测试和集成测试
