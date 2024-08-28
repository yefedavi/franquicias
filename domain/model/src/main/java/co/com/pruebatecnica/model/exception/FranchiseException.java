package co.com.pruebatecnica.model.exception;

import co.com.pruebatecnica.model.enums.ValidationErrorMessage;

public class FranchiseException extends RuntimeException{
    private final ValidationErrorMessage validationErrorMessage;

    public FranchiseException(ValidationErrorMessage validationErrorMessage) {
        super(validationErrorMessage.getMessage());
        this.validationErrorMessage = validationErrorMessage;

    }

    public FranchiseException(ValidationErrorMessage validationErrorMessage,String message) {
        super(message);
        this.validationErrorMessage = validationErrorMessage;

    }
}
