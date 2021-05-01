package heig.exception;

/**
 * Exception lancée lorsqu'il y a un problème dans les fichier de configuration de notre application
 * @author Eric Bousbaa & Ilias Goujgali
 * @version 1.0
 */
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
