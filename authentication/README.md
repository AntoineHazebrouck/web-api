# Run locally

```shell
mvn spring-boot:run "-Dspring-boot.run.profiles=local"
```

# Run tests

```shell
docker compose --file compose.test.yaml up --build --exit-code-from test-authentication-api
```

# Open API

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
