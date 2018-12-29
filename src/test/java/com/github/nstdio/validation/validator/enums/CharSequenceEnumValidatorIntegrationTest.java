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

package com.github.nstdio.validation.validator.enums;

import com.github.nstdio.validation.Enums;
import com.github.nstdio.validation.ValidatorIntegrationTest;
import com.github.nstdio.validation.constraint.Enum;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.hibernate.validator.testutil.ConstraintViolationAssert.*;

/**
 * @author Edgar Asatryan
 */
class CharSequenceEnumValidatorIntegrationTest extends ValidatorIntegrationTest {
    @Test
    void notValid() {
        Set<ConstraintViolation<EnumCSContainerAnnotated>> violations = validator.validate(EnumCSContainerAnnotated.of("one"));

        assertThat(violations).containsOnlyPaths(pathWith().property("value"));
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly("must be one of ONE, TWO, THREE");

        assertCorrectConstraintTypes(violations, Enum.class);
    }

    @Test
    void messageShouldBeReplaceable() {
        Set<ConstraintViolation<EnumCSContainerAnnotatedWithCustomMessage>> violations =
                validator.validate(EnumCSContainerAnnotatedWithCustomMessage.of("FIVE"));

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactly("The string FIVE must be one of enums Numbers [ONE, TWO, THREE]");
    }

    @Test
    void valid() {
        assertThat(validator.validate(EnumCSContainerAnnotated.of("ONE"))).isEmpty();
        assertThat(validator.validate(EnumCSContainerAnnotated.of("TWO"))).isEmpty();
        assertThat(validator.validate(EnumCSContainerAnnotated.of("THREE"))).isEmpty();
    }

    private enum Numbers {
        ONE,
        TWO,
        THREE
    }

    /**
     * Case sensitive.
     */
    private static class EnumCSContainerAnnotated {
        @Enum(Numbers.class)
        String value;

        static EnumCSContainerAnnotated of(String value) {
            EnumCSContainerAnnotated eva = new EnumCSContainerAnnotated();
            eva.value = value;
            return eva;
        }
    }

    private static class EmptyEnumContainerAnnotated {
        @Enum(Enums.Empty.class)
        String value;
    }

    /**
     * Case sensitive.
     */
    private static class EnumCSContainerAnnotatedWithCustomMessage {
        @Enum(value = Numbers.class, message = "The string ${validatedValue} must be one of enums ${value.getSimpleName()} [{enums}]")
        String value;

        static EnumCSContainerAnnotatedWithCustomMessage of(String value) {
            EnumCSContainerAnnotatedWithCustomMessage eva = new EnumCSContainerAnnotatedWithCustomMessage();
            eva.value = value;
            return eva;
        }
    }
}