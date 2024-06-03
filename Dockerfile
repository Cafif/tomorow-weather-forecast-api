FROM maven:3.8.5-openjdk-17 as BUILD
COPY ./forecast-api .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/forecast-api-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT["java", "-jar", "forecast-api.jar"]