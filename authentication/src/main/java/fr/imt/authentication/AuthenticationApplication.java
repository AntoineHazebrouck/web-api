package fr.imt.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class AuthenticationApplication {

	/**
	 * Run the application locally using : mvn spring-boot:run "-Dspring-boot.run.profiles=local".
	 * It is usefull to avoid containers.
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

}
