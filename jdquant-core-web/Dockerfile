FROM java:latest
MAINTAINER Zhiguo.Chen<chenzhiguo@jd.com>

VOLUME /tmp /exprot

ADD ./target/*.jar /app.jar

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
EXPOSE 8080
ENTRYPOINT ["java","-Djava.seurity.egd=file:/dev/./urandom","-jar","/app.jar"]