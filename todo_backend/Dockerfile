FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY build/libs/*.jar app.jar

# JAR 파일 실행
CMD ["java", "-jar", "/app/app.jar"]