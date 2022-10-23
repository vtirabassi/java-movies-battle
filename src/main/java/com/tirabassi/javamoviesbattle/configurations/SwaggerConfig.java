package com.tirabassi.javamoviesbattle.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tirabassi.javamoviesbattle.controllers"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Vendas API")
                .description("API para controle de vendas")
                .version("1.0")
                .build();

    }

    private ApiKey apiKey(){
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(securityReferenceList())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> securityReferenceList(){
        AuthorizationScope scope = new AuthorizationScope("global", "acsessAll");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = scope;

        SecurityReference securityReference = new SecurityReference("JWT", scopes);

        List<SecurityReference> securityReferenceList = new ArrayList<>();
        securityReferenceList.add(securityReference);

        return  securityReferenceList;
    }
}
