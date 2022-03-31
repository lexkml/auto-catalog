package com.kamelchukov.autocatalog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${keycloak.auth-server-url}")
    private String url;
    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(getInfo())
                .addSecurityItem(getSecurityRequirement())
                .components(getComponents());
    }

    private Components getComponents() {
        var authUrl = "%s/realms/%s/protocol/openid-connect/auth".formatted(url, realm);

        return new Components()
                .addSecuritySchemes("Security Scheme-1", new SecurityScheme()
                        .type(SecurityScheme.Type.OAUTH2)
                        .description("API using OAuth 2.0 protocol authorization.")
                        .flows(new OAuthFlows()
                                .implicit(new OAuthFlow()
                                        .authorizationUrl(authUrl))));
    }

    private SecurityRequirement getSecurityRequirement() {
        return new SecurityRequirement()
                .addList("Security Scheme-1");
    }

    private Info getInfo() {
        return new Info()
                .title("Auto Catalog API")
                .description("This is a catalog that contains data of cars and their owners")
                .version("1.0")
                .contact(new Contact()
                        .name("Alexandr Kamelchukov")
                        .email("lexkml@yandex.ru")
                        .url("https://github.com/lexkml"));
    }
}
