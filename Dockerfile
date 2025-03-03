FROM maven:3.8-openjdk-18-slim AS build
WORKDIR /app


COPY pom.xml .

# Build both frontend and backend
RUN mvn dependency:go-offline

# Run stage
FROM openjdk:18-jdk-slim
WORKDIR /app
COPY  target/*.jar app.jar
CMD ["./mvnw", "spring-boot:run"]
ENTRYPOINT ["java", "-jar", "app.jar"]