package org.rishabh.eventmanagementsystemadvanced.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Event Management System API" ,
                version = "1.0.0" ,
                description = "Backend services for events , tickets , payments , search , and user operations"
        )
)
public class SwaggerConfig {
}
