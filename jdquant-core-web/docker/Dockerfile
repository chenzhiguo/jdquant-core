#Notice：此Dockerfile依赖于Spotify的docker-maven-plugin执行！如若独自执行，请复制到target目录下执行再docker build命令
FROM java:latest
MAINTAINER Zhiguo.Chen<chenzhiguo@jd.com>

VOLUME /export

ADD ./*.jar /app.jar

#RUN cd /tmp/build && mvn -q dependency:resolve
#
#ADD src /tmp/build/src
#        #构建应用
#RUN cd /tmp/build && mvn -q -DskipTests=true package \
#        #拷贝编译结果到指定目录
#        && mv target/*.jar /app.jar \
#        #清理编译痕迹
#        && cd / && rm -rf /tmp/build

WORKDIR /
EXPOSE 8080 22006
ENTRYPOINT ["java","-Djava.seurity.egd=file:/dev/./urandom","-jar","/app.jar"]