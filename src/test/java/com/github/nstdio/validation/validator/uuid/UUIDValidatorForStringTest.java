package com.github.nstdio.validation.validator.uuid;

import com.github.nstdio.validation.constraint.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidator;

/**
 * @author Edgar Asatryan
 */
class UUIDValidatorForStringTest {

    private final ConstraintValidator<UUID, String> validator = new UUIDValidatorForString();

    @Test
    void dummy() {
        boolean isValid = validator.isValid("fde109f0-9f9b-44ab-a7bc-6de53a4ee7cd", null);

        Assertions.assertTrue(isValid);
    }
}