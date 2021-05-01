package heig.exception;

public class InvalidPrankConfiguration extends RuntimeException {
    public InvalidPrankConfiguration(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPrankConfiguration(Throwable cause) {
        super(cause);
    }

    public InvalidPrankConfiguration(String message) {
        super(message);
    }
}
