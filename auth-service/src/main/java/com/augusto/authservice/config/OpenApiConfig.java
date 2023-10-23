package com.augusto.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;


@Configuration
@OpenAPIDefinition(info = @Info(title = "Auth Service API", version = "v1",
        description = "Documentation of Auth service API"))
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info().title("Auth service API")
                        .version("VI")
                        .license(new License().name("APACHE 2.0").url("http://springdoc.org")));
    }
}
