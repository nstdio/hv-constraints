package com.github.nstdio.validation.validator.nonnullelements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Edgar Asatryan
 */
class NonNullElementsValidatorForObjectArrayTest {
    NonNullElementsValidatorForObjectArray validator;

    @BeforeEach
    void setUp() {
        validator = new NonNullElementsValidatorForObjectArray();
    }

    @Test
    void shouldBeValidWhenArrayIsNull() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    void shouldBeValidWhenArrayIsEmpty() {
        assertTrue(validator.isValid(new Object[0], null));
    }

    @Test
    void shouldBeValidWhenArrayDoesNotContainNulls() {
        assertTrue(validator.isValid(new String[]{"a", "b"}, null));
    }

    @Test
    void shouldBeNotValidWhenArrayContainNull() {
        assertFalse(validator.isValid(new String[]{"a", null}, null));
    }
}