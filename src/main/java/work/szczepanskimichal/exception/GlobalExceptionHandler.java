package work.szczepanskimichal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.ConnectException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConnectException.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        var connectionErrorMessage = "An connection error occurred: " + e.getMessage();
        return new ResponseEntity<>(connectionErrorMessage, HttpStatus.BAD_GATEWAY);
    }

}