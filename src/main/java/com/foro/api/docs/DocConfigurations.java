package com.foro.api.docs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocConfigurations {
   @Bean
   public OpenAPI customOpenAPI(){
       return new OpenAPI()
               .info(new Info()
                       .title("Foro-hub")
                       .description("This is a web api that contains crud methods of topic, course, response, teacher, student, user, role"))
               .components(new Components()
                       .addSecuritySchemes("bearer-key",
                       new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
   }
}
