package com.kamelchukov.autocatalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

public class SwaggerConfig {
    public OpenAPI myOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Auto catalog API")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("lexkml@redcollar.ru")
                                                .name("Alexandr Kamelchukov")
                                )
                );
    }
}
