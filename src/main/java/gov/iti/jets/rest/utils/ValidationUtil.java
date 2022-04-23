package gov.iti.jets.rest.utils;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ValidationUtil {

    private static final Validator validator;

    static {
        try ( var validatorFactory = Validation.buildDefaultValidatorFactory() ) {
            validator = validatorFactory.getValidator();
        }
    }

    public static <T> void validate( T bean ) {
        var constraintViolations = validator.validate( bean );
        if ( !constraintViolations.isEmpty() ) {
            throw new ConstraintViolationException( constraintViolations );
        }
    }
}
