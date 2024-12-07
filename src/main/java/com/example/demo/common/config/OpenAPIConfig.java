package com.example.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project Management API")
                        .description("プロジェクト管理システムのAPI仕様書")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("開発チーム")
                                .email("dev-team@example.com")));
    }
}
