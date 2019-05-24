/*
 * Copyright 2019 Edgar Asatryan <nstdio@gmail.com>
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

package com.github.nstdio.validation.validator.uuid;

import static org.hibernate.validator.testutil.ConstraintViolationAssert.assertThat;
import static org.hibernate.validator.testutil.ConstraintViolationAssert.pathWith;

import org.junit.jupiter.api.Test;

import com.github.nstdio.validation.ValidatorIntegrationTest;
import com.github.nstdio.validation.constraint.UUID;

import lombok.Value;

/**
 * @author Edgar Asatryan
 */
class UUIDValidatorForCharArrayIntegrationTest extends ValidatorIntegrationTest {

    @Test
    void shouldNotRejectValidValues() {
        Container container = Container.of(java.util.UUID.randomUUID().toString().toCharArray());

        assertThat(validator.validate(container)).isEmpty();
    }

    @Test
    void shouldNotRejectNullValue() {
        Container container = Container.of(null);
        assertThat(validator.validate(container)).isEmpty();
    }

    @Test
    void shouldRejectEmptyArray() {
        Container container = Container.of(new char[0]);
        assertThat(validator.validate(container))
                .containsOnlyPaths(pathWith().property("uuid"));
    }

    @Test
    void shouldRejectNilUUID() {
        ContainerRejectingNilUUID container = ContainerRejectingNilUUID.of("00000000-0000-0000-0000-000000000000".toCharArray());
        assertThat(validator.validate(container))
                .containsOnlyPaths(pathWith().property("uuid"));
    }

    @Value(staticConstructor = "of")
    private static class ContainerRejectingNilUUID {
        @UUID(allowNil = false)
        char[] uuid;
    }

    @Value(staticConstructor = "of")
    private static class Container {
        @UUID
        char[] uuid;
    }
}