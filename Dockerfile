FROM maven:3.8.4-openjdk-17-slim as build
WORKDIR /app
COPY mvnw .
COPY pom.xml .
COPY src src
EXPOSE 8083
RUN mvn package -DskipTests

FROM openjdk:17
COPY --from=build /app/target/ordersapi-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT java -jar -Djava.security.egd=file:/dev/./urandom -jar -XX:MinRAMPercentage=70 -XX:MaxRAMPercentage=90 -server -XX:+OptimizeStringConcat -XX:+UseStringDeduplication /app.jar
