package com.example.FrikadasVarias.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

//Configuration for OpenApi
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "PDA",
                        email = "example@gmail.com",
                        url = "https://example.com"
                ),
                description = "This is a sample API for Frikadas Varias",
                title = "Frikadas Varias API",
                version = "1.0",
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "http://swagger.io/terms/"

        ),
        //Servers
        servers = {
                @Server(
                        description = "Local server",
                        url = "http://localhost:9000"
                )
        }
)
//Security scheme
public class OpenApiConfig {
    //Anotation for OpenApiConfig

}
