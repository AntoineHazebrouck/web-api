### Groupe :

- Antoine HAZEBROUCK
- Lucas DUBUSSE
- Maxime SONTAG
- Milan DELZENNE

# Start the servers pool

```shell
docker compose up --build
```

Then open the web UI at [http://localhost:7070/index.html](http://localhost:7070/index.html).

# Open API

-   authentication : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

# Automated tests

### Authentication :

```shell
docker compose --file ./authentication/compose.test.yaml up --build --exit-code-from test-authentication-api
```

Tests are running within a docker compose. This is due to the fact that I could not find a decent option for integration tests based on the Mongo ecosystem (H2 equivalent).

Test cases are made using Gherkin under "src/test/resources/features".
