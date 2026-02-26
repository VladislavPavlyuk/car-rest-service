package ua.com.foxmined.carrestservice.exception;

/**
 * Base runtime exception for the application. All caught exceptions are wrapped and handled centrally.
 */
public class CarServiceException extends RuntimeException {

    public CarServiceException(String message) {
        super(message);
    }

    public CarServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarServiceException(Throwable cause) {
        super(cause);
    }
}
