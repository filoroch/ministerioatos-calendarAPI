package br.org.ministerioatos.calendarAPI.infrastructure.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryOpenAPI() {
        final String BEARER_AUTH = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Ministerio Atos - CalendarAPI")
                        .description("RESTful API para gerenciamento e exposição de dados de eventos estruturados")
                        .version("1.0.0")
                        .contact(new Contact().name("developer").email("dev.filoroch@gmail.com"))
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório e guia de contribuições")
                        .url("https://github.com/filoroch/ministerioatos-calendarAPI"))
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("Local"),
                        new Server().url("http://localhost:8080").description("HMG")))
                .tags(List.of(
                        new Tag().name("Evento").description("Gerenciamento de eventos para visualização no frontend do calendario"),
                        new Tag().name("Pessoas").description("Gerenciamento de pessoas com Login para administrar os eventos"),
                        new Tag().name("Login").description("Gerenciamento das sessões de login"),
                        new Tag().name("Departamento").description("Gerenciamento de departamentos para filtragem e organização dos eventos")))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH))
                .components(new Components().addSecuritySchemes(BEARER_AUTH,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}