# JDQuant Core Application

## 项目介绍

![logo](http://img30.360buyimg.com/jr_image/jfs/t4021/129/1804250943/13725/1410f124/5897eaa4N622347c1.png)

JDQuant是针对私募等专业投资团体开发的量化交易研究系统。

> PS: 项目目前依赖的是初始版本。

## 升级日志

>### 2017-07-03
>
> 1. 加入用户安全体系（基于Spring Security）
> 2. 增加Docker支持，自动编译Docker Image
>
>### 2017-06-15
>
> 1. 项目初始化


## 使用帮助

### 配置介绍

`第一步`：创建数据库
首先创建名称为jdquant数据库，编码格式UTF-8。然后将项目sql文件夹下面的create.sql导入到数据库，生成所需表代表成功导入。

`第二步`：编辑项目中application-*.yml，配置如下：

```yaml
######服务器设置######
server:
  port: 8080 #启动内嵌tomcat，设置其端口号
  contextPath: / #项目在tomcat中的contextPath
  session:
    timeout: 36000

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
  redis:
    database: 0
    host: 172.27.35.1
    port: 6379
    password:
    timeout: 0
    pool:
      max-active: 8
      max-wait: -1  # 连接池最大阻塞等待时间（使用负值表示没有限制）
      max-idle: 8   # 连接池中的最大空闲连接
      min-idle: 1   # 连接池中的最小空闲连接

  session:
    store-type: redis
    redis:
      namespace: jdquant-seesion

  devtools:
    restart:
      exclude: static/**,public/**
      enabled: false

######日志设置######
logging:
  path: /export/Logs/jdquant/ #日志文件目录
  file: ${logging.path}quant-core.log
  level: debug

######Thrift设置######
thrift:
  serverPort: 22006
  protocol: compact

######管理端设置######
management:
  port: 9090
  security:
    enabled: false

result:
  filePath: /export/Data/result/
```

`第三步`：打包运行项目
执行maven命令：`mvn clean package` 
* 方式1： 系统会自动编译打包成${filename}.jar文件，可以将该文件拷贝到服务器执行   
`java -jar ${filename}.jar`
* 方式2： 执行maven package命令同时会执行docker-maven-plugin的docker:build命令，会自动编译docker本地image。可执行命令运行容器：  
`docker run -d -p 8080:8080 -p 22006:22006 -p 9090:9090 --name jdquant-core -v /export:/export jdquant-core:${version}`

`第四步`：访问项目
启动web服务器后，访问地址：http://IP:[port]。

