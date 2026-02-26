package ua.com.foxmined.carrestservice.exception;

public class EntityNotPresentException extends Exception{

    public EntityNotPresentException(String errorMessage) {
        super(errorMessage);
    }

}
