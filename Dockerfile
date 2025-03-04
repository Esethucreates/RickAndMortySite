# Build stage
FROM maven:3.9.5-openjdk-21
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:21-slim
COPY --from=build /target/RickAndMortySite-0.0.1-SNAPSHOT.jar wiki.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","wiki.jar"]

