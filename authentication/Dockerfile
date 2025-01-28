FROM maven:3.8.5-openjdk-17-slim AS build
RUN mkdir -p /application
WORKDIR /application
COPY pom.xml /application
COPY src /application/src
RUN mvn -f pom.xml clean package -Dmaven.test.skip=true

FROM maven:3.8.5-openjdk-17-slim
COPY --from=build /application/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/application.jar"]