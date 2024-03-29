package work.szczepanskimichal.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super("authentication issue: " + message);
    }
}