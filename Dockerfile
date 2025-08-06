# 1단계: 빌드
FROM gradle:8.2.1-jdk17 AS build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle build -x test

# 2단계: 실행
FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]