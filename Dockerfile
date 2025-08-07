# 빌드 단계
FROM gradle:8.5-jdk17 AS build

WORKDIR /app
COPY --chown=gradle:gradle . .

# gradlew 권한 설정
RUN chmod +x ./gradlew

# 테스트 제외 빌드
RUN ./gradlew build -x test --no-daemon

# 실행 이미지
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]