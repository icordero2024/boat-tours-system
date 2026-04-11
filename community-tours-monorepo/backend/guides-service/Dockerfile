FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/target/guides-service.jar /app/guides-service.jar
EXPOSE 8082
CMD ["java", "-jar", "/app/guides-service.jar"]
