package hiberspring.util.impl;

import hiberspring.util.ValidationUtil;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidationUtilImpl implements ValidationUtil {
    private final Validator validator;

    public ValidationUtilImpl() {
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).isEmpty();
    }
}
