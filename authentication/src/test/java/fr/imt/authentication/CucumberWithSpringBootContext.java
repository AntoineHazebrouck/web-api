package fr.imt.authentication;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.cucumber.spring.CucumberContextConfiguration;

// @ActiveProfiles("local")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public final class CucumberWithSpringBootContext {
}