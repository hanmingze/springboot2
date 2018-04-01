#打包
mvn package
#启动
java -jar target/spring-boot-docker-1.0.jar
看到 Spring Boot 的启动日志后表明环境配置没有问题，接下来我们使用 DockerFile 构建镜像。

#注意Dockerfile中的版本 和 pom中的version一致
mvn package docker:build

使用docker images命令查看构建好的镜像：

下一步就是运行该镜像

docker run -p 8080:8080 -t springboot/docker
