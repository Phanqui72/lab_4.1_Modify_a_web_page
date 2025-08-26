# Giai đoạn 1: Build ứng dụng bằng Maven
# Sử dụng một image chứa sẵn Maven 3.6+ và Java (ví dụ: JDK 17)
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Tạo thư mục làm việc
WORKDIR /app

# Tối ưu hóa Docker caching:
# Chỉ copy pom.xml trước để tải các thư viện.
# Nếu chỉ code thay đổi mà pom.xml không đổi, Docker sẽ dùng lại layer này.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy toàn bộ mã nguồn vào
COPY src ./src

# Chạy lệnh build của Maven để tạo ra file .war
# File WAR sẽ được tạo trong thư mục /app/target/
RUN mvn clean package

# Giai đoạn 2: Chạy ứng dụng trên server Tomcat
# Sử dụng image Tomcat 11 chính thức với JDK tương ứng
FROM tomcat:11.0.10-jdk17-temurin

# Xóa các webapp mặc định của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR đã được build từ giai đoạn 1 vào thư mục webapps của Tomcat.
# Đổi tên nó thành ROOT.war để nó chạy ở thư mục gốc của domain (ví dụ: my-app.onrender.com/)
# Tên file WAR được xác định trong pom.xml (<finalName>)
COPY --from=build /app/target/ch04_ex1_survey.war /usr/local/tomcat/webapps/ROOT.war

# Tomcat image sẽ tự động chạy server khi container khởi động.
# Expose cổng 8080 mà Tomcat lắng nghe.
EXPOSE 8080