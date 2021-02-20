package com.api.phonebook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                .basePackage("com.api.phonebook"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
            "PhoneBook API Documentation", 
            "Refer this Documentation to get started with the API",
            "1.0", 
            "For Learning Purpose",
            new Contact("Sandeep Maithani", "https://www.linkedin.com/in/smaithani/", "Sandeep1137@gmail.com"),
            "Apache License Version 2.0",
            "https://www.apache.org/license.html");

            return apiInfo;
    }
}