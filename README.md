# JDQuant Core Application

## 项目介绍

![logo](http://img30.360buyimg.com/jr_image/jfs/t4021/129/1804250943/13725/1410f124/5897eaa4N622347c1.png)

JDQuant是针对私募等专业投资团体开发的量化交易研究系统。

> PS: 项目目前依赖的是初始版本。

## 升级日志

>### 2017-06-15
>
> 1. 项目初始化。


## 使用帮助

### 配置介绍

`第一步`：创建数据库
首先创建名称为jdquant数据库，编码格式UTF-8。然后将项目sql文件夹下面的create.sql导入到数据库，生成所需表代表成功导入。

`第二步`：编辑项目中application-*.yml，配置如下：

```yaml
######服务器设置######
server:
  port: 8080 #启动内嵌tomcat，设置其端口号
  contextPath: /core #项目在tomcat中的contextPath

######Spring设置######
spring:
  aop:
    auto: true
  http:
    encoding:
        charset: UTF-8
        enabled: true
        force: true
  datasource:
    url: jdbc:mysql://172.24.7.226:3306/jdquant?useUnicode=true&characterEncoding=utf8
    username: root
    password: 1qaz2wsx
    driver-class-name: com.mysql.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource

  session:
    store-type: none

######日志设置######
logging:
  path: /export/Logs/jdquant/ #日志文件目录
  file: ${logging.path}quant-core.log

######Thrift设置######
thrift:
  serverPort: 22006
  protocol: compact
```

`第三步`：打包运行项目
执行maven命令：mvn clean package
分多种启动方式，后续填补...

`第四步`：访问项目
启动web服务器后，访问地址：http://IP:[port]/core。

## 服务提供端配置
