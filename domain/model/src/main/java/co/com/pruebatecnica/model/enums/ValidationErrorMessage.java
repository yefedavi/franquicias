package co.com.pruebatecnica.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ValidationErrorMessage {
    FRANCHISE_EXISTS("001", "Franchise exists"),
    FRANCHISE_DOES_NOT_EXISTS("002", "Franchise does not exists"),
    PRODUCT_DOES_NOT_EXISTS("003", "Product does not exists"),
    BRANCH_OFFICE_DOES_NOT_EXISTS("004", "Branch office does not exists"),
    DYNAMODB_SAVE_ERROR("005", "Error save dynamodb"),
    DYNAMODB_UPDATE_ERROR("006", "Error update dynamodb");
    private final String code;
    private final String message;
}