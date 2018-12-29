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