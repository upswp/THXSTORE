FROM openjdk:11 AS builder
WORKDIR /backend
COPY . .
RUN chmod +x ./gradlew

RUN ./gradlew :module-web:clean build
RUN ls module-web/build/libs

FROM adoptopenjdk:11-jdk
COPY --from=builder /backend/module-web/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "./app.jar"]
