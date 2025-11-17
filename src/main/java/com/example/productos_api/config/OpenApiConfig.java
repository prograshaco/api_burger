package com.example.productos_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Burger - Gestión de Productos")
                        .version("1.0.0")
                        .description("API REST para la gestión de productos del restaurante. " +
                                "Permite crear, leer, actualizar y eliminar productos.")
                        .contact(new Contact()
                                .name("Prograshaco")
                                .url("https://github.com/prograshaco/api_burger")));
    }
}
