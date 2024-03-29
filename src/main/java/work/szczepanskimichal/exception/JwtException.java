package work.szczepanskimichal.exception;

public class JwtException extends RuntimeException {

    public JwtException() {
        super("invalid or expired jwt");
    }
}