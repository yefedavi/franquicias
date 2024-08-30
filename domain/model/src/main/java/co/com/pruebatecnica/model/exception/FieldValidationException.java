package co.com.pruebatecnica.model.exception;

public class FieldValidationException extends RuntimeException{
    public FieldValidationException(String message) {
        super(message);
    }
}
