FROM maven:3.8.5-openjdk-17-slim AS build
RUN mkdir -p /application
WORKDIR /application
COPY pom.xml /application
COPY src /application/src
# RUN mvn -f pom.xml clean package -Dmaven.test.skip=true
ENTRYPOINT ["mvn", "-f", "pom.xml", "clean", "test"]

# FROM maven:3.9.9-amazoncorretto-17-debian-bookworm
# COPY --from=build /application/target/*.jar application.jar
# EXPOSE 8080
# # ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/application.jar"]
# ENTRYPOINT ["mvn", "clean", "install"]