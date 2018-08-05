package com.github.nstdio.validation.validator.nonnullelements;

import com.github.nstdio.validation.constraint.NonNullElements;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * @author Edgar Asatryan
 */
public class NonNullElementsValidatorForObjectArray implements ConstraintValidator<NonNullElements, Object[]> {
    @Override
    public boolean isValid(Object[] value, ConstraintValidatorContext context) {
        if (value == null || value.length == 0)
            return true;

        return NonNullElementsForList.doValidation(Arrays.asList(value), context);
    }
}
