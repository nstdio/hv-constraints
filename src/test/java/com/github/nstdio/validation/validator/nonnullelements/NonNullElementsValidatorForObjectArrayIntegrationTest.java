package com.github.nstdio.validation.validator.nonnullelements;

import com.github.nstdio.validation.ValidatorIntegrationTest;
import com.github.nstdio.validation.constraint.NonNullElements;
import lombok.Value;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.hibernate.validator.testutil.ConstraintViolationAssert.assertThat;

/**
 * @author Edgar Asatryan
 */
class NonNullElementsValidatorForObjectArrayIntegrationTest extends ValidatorIntegrationTest {
    @Test
    void shouldContainMessageWithNullIndices() {
        Set<ConstraintViolation<MessageTest>> violations = validator.validate(MessageTest.of(new String[]{null, "abc", "d", null, "abc", null}));

        Assertions.assertThat(violations).hasSize(1);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly("contains nulls at 0, 3, 5 indices.");
    }

    @Test
    void shouldBeValidWhenArrayDoesNotContainNulls() {
        assertThat(validator.validate(ValidTest.of(new String[]{"abc", "d", "abc", ""}))).isEmpty();
    }

    @Value(staticConstructor = "of")
    private static class MessageTest {
        @NonNullElements(message = "contains nulls at {indices} indices.")
        String[] value;
    }

    @Value(staticConstructor = "of")
    private static class ValidTest {
        @NonNullElements
        String[] value;
    }
}