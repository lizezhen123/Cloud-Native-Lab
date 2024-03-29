# 构建阶段
FROM openjdk:17-jdk as builder

# 设置工作目录
WORKDIR /app

# 复制项目文件
COPY . /app

# 构建可执行文件
RUN ./mvnw clean package -pl ase_contribute_service -am -Dmaven.test.skip=true

# 最终镜像
FROM openjdk:17-jdk as prod

# 设置工作目录
WORKDIR /app


# 从构建阶段复制可执行文件到最终镜像
COPY --from=builder /app/ase_contribute_service/target/*.jar /app/



# 暴露应用程序运行的端口
EXPOSE 8080

# 定义容器启动时运行的命令
CMD ["java", "-jar",  "/app/ase_contribute_service-0.0.1-SNAPSHOT.jar", "--spring.config.location=classpath:/application-prod.yml"]