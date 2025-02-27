package com.farmCity.farm_city_be.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Farmers Market",
                        email = "marketdefarmers@gmail.com"
                ),
                description = "OpenApi documentation for Farmers Market",
                title = "OpenApi Specification - PSL",
                version = "1.1",
                license = @License(
                        name = "License name",
                        url = "http://some-url.com"
                ),
                termsOfService = "Terms of Service"

        ),
        servers = {
                @Server(
                        description = "Local Env",
                        url = "http://localhost:4444"
                ),
                @Server(
                        description = "Online Env",
                        url = ""
                )
        },
        security = {
                @SecurityRequirement(
                        name = "BearerAuth"
                )
        }
)
@SecurityScheme(
        name = "BearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}