package avmp.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI defineOpenAPI() {
        Server server = new Server();
        server.setUrl("");
        server.setDescription("Development Server");
        
        Contact contact = new Contact();
        contact.setUrl("https://github.com/MinAnnie/");
        contact.setName("Angie Matiz");
        contact.setEmail("matizangie6@gmail.com");
        
        Info info = new Info()
                .title("Api para probar CI/CD")
                .version("1.0")
                .description("API para probar CI/CD")
                .contact(contact);
        
        return new OpenAPI().info(info).servers(List.of(server));
    }
}
