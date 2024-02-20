package com.neobis.neoCafe.configuration;

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
                        name = "Azamat",
                        email = "Telegram: @Azamat_B7"
                ),
                description = "OpenApi documentation for NeoCafe endpoints",
                title = "OpenApi specification - neoCafe ",
                version = "1.0",
                license = @License(
                        name = "My license name",
                        url = "https://my-url.com"
                ),
                termsOfService = "Terms of service"

        ),
        servers = {
                @Server(
                        description = "HTTP Server", url = "http://neocafe-production.up.railway.app"
                ),
                @Server(
                        description = "HTTPS Server", url = "https://neocafe-production.up.railway.app"
                )
        },
        security = @SecurityRequirement(
                name = "bearerAuth"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
