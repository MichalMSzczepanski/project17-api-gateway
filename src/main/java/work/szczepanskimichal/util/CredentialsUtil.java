package work.szczepanskimichal.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
@Getter
@Setter
public class CredentialsUtil {
    String key;
}
