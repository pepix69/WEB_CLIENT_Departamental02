package com.example.examen_02.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Productos",
                description = "Documentación del segundo examen departamental",
                version = "1.0.0",
                contact = @Contact(
                        name = "José Ángel Montoya Zúñiga",
                        email = "joseangelmontoyz69@gmail.com",
                        url = "http://localhost:8081/contacto"
                ),
                license = @License(),
                termsOfService = "Solo se permiten 400 solicitudes diarias"

        ),
        servers = {
                @Server(
                        description = "Servidor de pruebas",
                        url = "http://localhost:8080"
                ),

                @Server(
                        description = "Servidor de produccion",
                        url = "http://localhost:8080/api/v1/equipo"
                )

        },
        tags = {
                @Tag(
                        name = "Productos",
                        description = "Endpoints para los recursos del equipo"
                )
        }
)

public class OpenApiConfiguration {
}
