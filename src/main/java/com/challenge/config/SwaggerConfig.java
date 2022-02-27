package com.challenge.config;

import org.assertj.core.util.Lists;
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

@Configuration
public class SwaggerConfig {

    /* API details */
    private static final String API_TITLE = "Challenge v2 API";
    private static final String API_DESCRIPTION = "Energy asset module";
    private static final String API_VERSION = "1.0";

    private static final String REQUEST_HANDLER_SELECTORS_BASE_PACKAGE = "com.challenge.resource";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(REQUEST_HANDLER_SELECTORS_BASE_PACKAGE + ".api"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API info
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .build();
    }
}

