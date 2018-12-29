/*
 * Copyright 2018 Edgar Asatryan <nstdio@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.nstdio.validation.validator.nonnullelements;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;

import com.github.nstdio.validation.ValidatorIntegrationTest;
import com.github.nstdio.validation.constraint.NonNullElements;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import lombok.Value;

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