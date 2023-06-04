package com.gestion.comercial;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = PropertyPlaceholderAutoConfiguration.class)
public class ComercialApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComercialApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI().info(new Info()
				.title("KARU Spring Boot 3 API")
				.version("1.0.3")
				.description("Picnic app Spring Boot 3 with Swagger")
				.termsOfService("https://swagger.io/terms")
				.license(new License().name("Apache 2.0").url("https://springdoc.org")));
	}
}
