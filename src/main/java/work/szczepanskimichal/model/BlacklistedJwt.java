package work.szczepanskimichal.model;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Document(collection = "blacklisted_jwts")
public class BlacklistedJwt {
    @Id
    private String id;
    private String jwt;
}