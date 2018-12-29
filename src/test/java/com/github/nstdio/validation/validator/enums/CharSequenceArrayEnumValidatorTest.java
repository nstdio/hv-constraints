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
import com.github.nstdio.validation.validator.enums.CharSequenceArrayEnumValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.nstdio.validation.AnnotationFactory.enumAnnotation;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Edgar Asatryan
 */
class CharSequenceArrayEnumValidatorTest {
    CharSequenceArrayEnumValidator validator;

    private static Stream<List<String>> validUpperCaseRoles() {
        return Stream.of(
                Arrays.asList("SYSTEM", "DEVELOPER", "MASTER_ADMIN", "PLAIN_ADMIN", "USER", "GUEST", "TEMP"),
                Arrays.asList("DEVELOPER", "MASTER_ADMIN", "PLAIN_ADMIN", "USER", "GUEST", "TEMP"),
                Arrays.asList("MASTER_ADMIN", "PLAIN_ADMIN", "USER", "GUEST", "TEMP"),
                Arrays.asList("PLAIN_ADMIN", "USER", "GUEST", "TEMP"),
                Arrays.asList("USER", "GUEST", "TEMP"),
                Arrays.asList("GUEST", "TEMP"),
                Arrays.asList("TEMP"),
                Arrays.asList()
        );
    }

    private static Stream<List<String>> validLowerCaseRoles() {
        return validUpperCaseRoles()
                .map(strings -> strings.stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList()));
    }

    private static Stream<String> notJavaIdentifiers() {
        return Stream.of("-", "", "\n", "#", "-abc", "");
    }

    @BeforeEach
    void setUp() {
        validator = new CharSequenceArrayEnumValidator();
        validator.initialize(enumAnnotation(Enums.RoleUpperCase.class));
    }

    @ParameterizedTest
    @MethodSource("validUpperCaseRoles")
    void validUpperCaseRoles(List<String> roles) {
        assertThat(validator.isValid(roles.toArray(new CharSequence[0]), null)).isTrue();
    }

    @Test
    void notValidWhenInvalidValueSupplied() {
        String[] strings = {"SYSTEM", "ABC"};
        assertThat(validator.isValid(strings, null)).isFalse();
    }

    @Test
    void nullsShouldBeFiltered() {
        validator.initialize(enumAnnotation(Enums.RoleUpperCase.class));
        String[] roles = {"SYSTEM", null};

        assertThat(validator.isValid(roles, null)).isTrue();
    }

    @Test
    void behaviorWhenAllElementsAreNull() {
        validator.initialize(enumAnnotation(Enums.RoleUpperCase.class));
        String[] roles = {null, null};

        assertThat(validator.isValid(roles, null)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("validUpperCaseRoles")
    void shouldBeValidWhenCaseInsensitive2(List<String> roles) {
        validator.initialize(enumAnnotation(Enums.RoleLowerCase.class, false));

        assertThat(validator.isValid(roles.toArray(new CharSequence[0]), null)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("validLowerCaseRoles")
    void shouldBeValidWhenCaseInsensitive3(List<String> roles) {
        validator.initialize(enumAnnotation(Enums.RoleUpperCase.class, false));

        assertThat(validator.isValid(roles.toArray(new CharSequence[0]), null)).isTrue();
    }

    @Test
    void shouldBeValidWhenFailOnEmptyIsFalse() {
        validator.initialize(enumAnnotation(Enums.Empty.class, false, false));

        assertThat(validator.isValid(new String[]{"a", "b", "c"}, null)).isTrue();
    }

    @Test
    void shouldBeNotValidWhenFailOnEmptyIsTrue() {
        validator.initialize(enumAnnotation(Enums.Empty.class));

        assertThat(validator.isValid(new String[]{"a", "b", "c"}, null)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("notJavaIdentifiers")
    void whenEnumIsEmptyAndFailOnIsTrueAndValueIsNotJavaIdentifier(String str) {
        validator.initialize(enumAnnotation(Enums.Empty.class, true, false));

        assertThat(validator.isValid(new String[]{str}, null)).isFalse();
    }
}