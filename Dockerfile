# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean install -DskipTests


FROM openjdk:21
COPY --from=build /target/RickAndMortySite-0.0.1.jar wiki.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","wiki.jar"]

