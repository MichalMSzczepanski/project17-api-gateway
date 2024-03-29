package work.szczepanskimichal.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import work.szczepanskimichal.util.CredentialsUtil;

@Configuration
@EnableConfigurationProperties(CredentialsUtil.class)
public class PropertiesConfiguration {
    @Configuration
    @Profile("test")
    @PropertySource(name = "JwtCredentials", value = "classpath:/test/credentials.yaml", factory =
            YamlPropertyFactorySource.class)
    public static class TestPropertiesConfiguration {

    }

    @Configuration
    @Profile("local")
    @PropertySource(name = "JwtCredentials", value = "classpath:/local/credentials.yaml", factory =
            YamlPropertyFactorySource.class)
    public static class LocalPropertiesConfiguration {

    }

    @Configuration
    @Profile("prod")
    @PropertySource(name = "JwtCredentials", value = "classpath:/prod/credentials.yaml", factory =
            YamlPropertyFactorySource.class)
    public static class ProdPropertiesConfiguration {

    }
}
