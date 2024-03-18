package work.szczepanskimichal.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class LoginResponse {
    private String id;
    private String email;
    private HttpStatus responseStatus;
    private String message;
    private LocalDateTime timeStamp;
}
