package com.github.nstdio.validation.validator.positive;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Positive;
import java.util.stream.IntStream;

/**
 * @author Edgar Asatryan
 */
public class PositiveValidatorForIntArray implements ConstraintValidator<Positive, int[]> {
    @Override
    public boolean isValid(int[] value, ConstraintValidatorContext context) {
        if (value == null || value.length == 0) {
            return true;
        }

        return IntStream.of(value).allMatch(v -> v > 0);
    }
}
