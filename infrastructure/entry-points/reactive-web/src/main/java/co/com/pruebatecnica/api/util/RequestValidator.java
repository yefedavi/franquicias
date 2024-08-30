package co.com.pruebatecnica.api.util;

import co.com.pruebatecnica.model.exception.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class RequestValidator {
    @Autowired
    Validator validator;

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> Mono<T> validate(T obj) {

        if (obj == null) {
            return Mono.error(new IllegalArgumentException());
        }

        Errors errors = new BeanPropertyBindingResult(
                obj,
                obj.getClass().getName());

        this.validator.validate(obj,errors);

        if(errors.getFieldErrors() != null && !errors.getFieldErrors().isEmpty()) {
            return Mono.error(new FieldValidationException(errors.getFieldErrors().stream()
                    .map(field -> field.getField() + " " + field.getDefaultMessage()).collect(Collectors.joining(", "))));
        }

        return Mono.just(obj);


    }
}
