FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:17
EXPOSE 8082
WORKDIR /app
COPY --from=builder /app/target/*.jar ./collection_backend.jar

CMD ["/usr/bin/java", "-jar", "/app/collection_backend.jar"]