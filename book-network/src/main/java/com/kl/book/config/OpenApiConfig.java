package com.kl.book.config;

import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Kantha",
                        email = "kaitlinrose00@gmail.com",
                        url = "https://test.com"
                ),
                description = "OpenApi Documentation for spring security",
                title = "OpenApi specification - kantha",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://some-license.com"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = {@Server(
                description = "local ENV",
                url = "http://localhost:8088/api/v1"
        ),
                @Server(
                        description = "PROD Env",
                        url = "https://test.com"
                )
        },
        security = {
                @SecurityRequirement(
                        name="bearerAuth"

                )
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme ="bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
