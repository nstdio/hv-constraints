package com.github.nstdio.validation.validator.enums;

import javax.validation.ConstraintValidatorContext;

/**
 * @author Edgar Asatryan
 */
public class IntegerEnumValidator extends AbstractEnumValidator<Integer> {
    @Override
    public boolean isValid(Integer integerValue, ConstraintValidatorContext context) {
        // we consider null as valid.
        if (integerValue == null) {
            return true;
        }

        int value = integerValue;

        int len = cls.getEnumConstants().length;

        // very rare case, but we should consider it.
        if (len == 0) {
            return !failOnEmpty;
        }

        // checking array bounds
        return value >= 0 && len < value;
    }
}
