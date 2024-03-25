package work.szczepanskimichal.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import work.szczepanskimichal.configuration.ServiceAddressConfiguration;

import java.net.ConnectException;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final ServiceAddressConfiguration serviceAddressConfiguration;

    @ExceptionHandler(ConnectException.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        var connectionErrorMessage = "An connection error occurred: " + e.getMessage();
        log.error(connectionErrorMessage);
//        log.error("calling user-service at: " + serviceAddressConfiguration.user);
        return new ResponseEntity<>(connectionErrorMessage, HttpStatus.BAD_GATEWAY);
    }

}