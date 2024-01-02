package ua.org.shpp.todolist.cofiguration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${application.openapi.dev-url}")
    private String devUrl;

    @Value("${application.openapi.prod-url}")
    private String prodUrl;

    @Value("${application.version}")
    private String version;

    @Bean
    public OpenAPI openApiInformation() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact()
                .email("zlatnikovyevgeniy@gmail.com")
                .name("Yevgeniy Pustovit");

        Info info = new Info()
                .contact(contact)
                .description("Application with simple controller that realizes crud operations.")
                .title("Web application")
                .version(version)
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }

}