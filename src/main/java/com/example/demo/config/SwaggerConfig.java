package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "여정",
                description = "여정 API",
                version = "v1"))
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi group1() {
        String[] paths = {"/api/v1/members", "/api/v1/members/**"};

        return GroupedOpenApi.builder()
                .group("유저 API")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi group2() {
        String[] paths = {"/api/v1/boards", "/api/v1/boards/**"};

        return GroupedOpenApi.builder()
                .group("게시글 API")
                .pathsToMatch(paths)
                .build();
    }
}
