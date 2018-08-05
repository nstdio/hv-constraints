package com.github.nstdio.validation.validator.positive;

import com.github.nstdio.validation.ValidatorTest;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidator;
import javax.validation.constraints.Positive;

/**
 * @author Edgar Asatryan
 */
class PositiveValidatorForIntArrayTest extends ValidatorTest {
    private final PositiveValidatorForIntArray validator = new PositiveValidatorForIntArray();

    @Test
    void shouldBeValidWhenValidatedValueIsNull() {
        isValid(null);
    }

    @Test
    void shouldBeValidWhenValidatedValueIsEmpty() {
        isValid(new int[0]);
    }

    @Test
    void shouldBeValidWhenNoNegativeOrZeroValuesArePresent() {
        isValid(new int[]{1, 2, 3, 4});
    }

    @Test
    void shouldBeNotValidZeroPresent() {
        isNotValid(new int[]{1, 2, 3, 4, 0});
    }

    @Test
    void shouldBeNotValidNegativePresent() {
        isNotValid(new int[]{1, 2, 3, 4, -1});
    }

    @Override
    @SuppressWarnings("unchecked")
    public ConstraintValidator<Positive, int[]> validator() {
        return validator;
    }
}