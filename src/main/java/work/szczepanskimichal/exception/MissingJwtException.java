package work.szczepanskimichal.exception;

public class MissingJwtException extends RuntimeException {

    public MissingJwtException() {
        super("missing jwt");
    }
}