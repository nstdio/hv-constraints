package com.github.nstdio.validation.validator.nonnullelements;

import com.github.nstdio.validation.constraint.NonNullElements;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author Edgar Asatryan
 */
public class NonNullElementsForIterable implements ConstraintValidator<NonNullElements, Iterable> {
    @Override
    public boolean isValid(Iterable value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value instanceof List) {
            return NonNullElementsForList.doValidation((List) value, context);
        }

        for (Object e : value) {
            if (e == null)
                return false;
        }

        return true;
    }
}
