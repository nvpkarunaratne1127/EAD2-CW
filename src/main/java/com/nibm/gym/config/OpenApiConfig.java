package com.nibm.gym.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gymManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("NIBM Gym Management System - RESTful API")
                        .description("Enterprise Application Development 02 (EAD 02) Coursework REST API for NIBM HDSE 26.2FT. " +
                                "Provides full CRUD operations for Equipment, Supplements, Trainers, Memberships, Workout Plans, and Invoicing.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("NIBM HDSE Student")
                                .email("student@nibm.lk"))
                        .license(new License().name("Educational Use - NIBM HDSE 26.2FT")));
    }
}
