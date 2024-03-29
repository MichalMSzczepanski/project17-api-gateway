package work.szczepanskimichal.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class LoginResponse {
    private String id;
    private UUID userId;
    private String email;
    private HttpStatus responseStatus;
    private String message;
    private LocalDateTime timeStamp;
}
