package co.com.pruebatecnica.api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Franquicias").description("Prueba tecnica de api franquicias").version("1.0")
                        .license(new License().name("Nequi").url("https://github.com/yefedavi/franquicias")))
                .externalDocs(new ExternalDocumentation().description("Test Documentation").url("https://github.com/yefedavi/franquicias/README.md"));
    }

    @Bean
    public GroupedOpenApi httpApi() {
        return GroupedOpenApi.builder()
                .group("publi-apis")
                .pathsToMatch("api/**")
                .build();
    }
}
