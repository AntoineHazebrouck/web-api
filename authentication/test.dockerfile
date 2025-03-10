FROM maven:3.8.5-openjdk-17-slim AS build
RUN mkdir -p /application
WORKDIR /application
COPY pom.xml /application
COPY src /application/src
ENTRYPOINT ["mvn", "-f", "pom.xml", "clean", "test"]