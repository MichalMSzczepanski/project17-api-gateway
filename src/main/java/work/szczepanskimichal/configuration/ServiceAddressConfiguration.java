package work.szczepanskimichal.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "services.address")
@Getter
@Setter
public class ServiceAddressConfiguration {

    public String user;
    public String present;

}
