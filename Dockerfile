#
# Build stage
#
FROM maven:3.8.2-jdk-18.0.2 AS build
COPY . .
RUN mvn clean package

#
# Package stage
#
FROM openjdk:18.0.2-jdk-slim
COPY --from=build /target/api-gc.war api-gc.war
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
