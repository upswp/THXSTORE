FROM openjdk:11-alpine AS builder 
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build

FROM adoptopenjdk:11-jdk
COPY --from=builder build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]