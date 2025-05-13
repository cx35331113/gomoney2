# gomoney-oauth2.1

## 系统说明

- 基于 Spring Cloud 2024 、Spring Boot 3.x、 OAuth2 的 RBAC **权限管理系统**
- 前端vue3+element-ui
- 提供 lambda 、stream api 、webflux 的生产实践- 
### 核心依赖
| 依赖                                         | 版本         |
| ------------------------------------------  |------------|
| Spring Boot                                 | 3.4.5      |
| Spring Cloud                                | 2024.0.1   |
| Spring Cloud Alibaba                        | 2023.0.3.2 |
| Spring Security OAuth2 Authorization Server | 1.4.3      |
| Mybatis Plus                                | 3.5.9      |
| hutool                                      | 5.8.8      |
| swagger                                     | 3.0.0      |

### 模块说明
vue2前端
gomoeny-oauth2-front  -- https://gitee.com/chenxiang_1984/gomoney-oauth2.1-ui.git


gomoeny-oauth2
├── common         -- 系统公共模块
├── gateway        -- 网关服务[9999]
├── gomoney-oauth2 -- 授权服务[6100]
├── monitor        -- 服务监控[8000]
├── gomoney-biz    -- 授权资源服务[6000]
├── gomoney-ai    -- ai业务服务[8666]


#### 软件架构
gateway        网关
gomoney-oauth2 授权服务器
monitor        服务监控
gomoney-biz    授权资源服务

#### 安装教程

1. 优先需要执行gomoney-oauth2项目下db文件中的datatable.sql和init.sql数据库脚本
   2、 修改hosts
   127.0.0.1   gomoney-mysql
   127.0.0.1   gomoney-redis
   127.0.0.1   gomoney-nacos
   127.0.0.1   gomoney-monitor
#### 使用说明R

1.  访问nacos.io下载nacos2.0.3并运行
2.  下载redis不需要设置密码然后运行
3.  优先启动gateway服务 其他服务随便启动

#### 参与贡献
期待你的贡献

#### 特技
无
