# Giai đoạn 1: Build ứng dụng bằng Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

# CẬP NHẬT ĐƯỜNG DẪN Ở ĐÂY
COPY src/main ./src/main

RUN mvn clean package

# Giai đoạn 2: Chạy ứng dụng trên server Tomcat
FROM tomcat:11.0.10-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*

# Tên artifactId trong pom.xml là 'ch04_ex1_survey', nên file war sẽ là ch04_ex1_survey.war
# Nếu bạn đổi artifactId, hãy đổi tên file ở đây
COPY --from=build /app/target/ch04_ex1_survey.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080