package com.github.nstdio.validation.validator.nonnullelements;

import com.github.nstdio.validation.ValidatorIntegrationTest;
import com.github.nstdio.validation.constraint.NonNullElements;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import lombok.Value;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Edgar Asatryan
 */
class NonNullElementsForIterableIntegrationTest extends ValidatorIntegrationTest {

    @Test
    void shouldBeValidIfDoesNotContainNulls() {
        RawTypeCollectionContainer container = RawTypeCollectionContainer.of(ImmutableSet.of("a", "b", "c"));

        assertThat(validator.validate(container)).isEmpty();
    }

    @Test
    void shouldInterpolateIndicesWhenIterableTypeIsList() {
        RawIterableContainer container = RawIterableContainer.of(Arrays.asList("a", "b", null, null, "c"));

        ConstraintViolation<RawIterableContainer> violation = Iterables.getOnlyElement(validator.validate(container));

        assertThat(violation.getMessage())
                .isEqualTo("The iterable contains nulls on 2, 3 indices.");
    }

    @Test
    void shouldNotInterpolateIndicesWhenIterableIsNotIndexBased() {
        RawIterableContainer container = RawIterableContainer.of(Sets.newHashSet("a", "b", null, null, "c"));

        ConstraintViolation<RawIterableContainer> violation = Iterables.getOnlyElement(validator.validate(container));

        assertThat(violation.getMessage())
                .isEqualTo("The iterable contains nulls on {indices} indices.");
    }

    @Value(staticConstructor = "of")
    private static class RawTypeCollectionContainer {
        @NonNullElements
        Collection value;
    }

    @Value(staticConstructor = "of")
    private static class RawIterableContainer {
        @NonNullElements(message = "The iterable contains nulls on {indices} indices.")
        Iterable value;
    }
}