package work.szczepanskimichal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import work.szczepanskimichal.configuration.ServiceAddressConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ServiceAddressConfiguration.class)
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
