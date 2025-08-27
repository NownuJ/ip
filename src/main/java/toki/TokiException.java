package toki;

/**
 * Domain-specific checked exception used to signal user-facing errors
 * (e.g., parse errors, invalid indices, malformed dates) within the Toki app.
 */

public class TokiException extends Exception {

    public TokiException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokiException(String message) {
        super(message);
    }
}
