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

package com.github.nstdio.validation.validator.uuid;

import com.github.nstdio.validation.ValidatorIntegrationTest;
import com.github.nstdio.validation.constraint.UUID;
import lombok.Value;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.hibernate.validator.testutil.ConstraintViolationAssert.assertThat;
import static org.hibernate.validator.testutil.ConstraintViolationAssert.pathWith;

/**
 * @author Edgar Asatryan
 */
class UUIDValidatorForCharSequenceIntegrationTest extends ValidatorIntegrationTest {
    private static final String NIL_UUID = "00000000-0000-0000-0000-000000000000";

    @Test
    void shouldRejectNilUUID() {
        ContainerRejectingNilUUID container = ContainerRejectingNilUUID.of(NIL_UUID);
        Set<ConstraintViolation<ContainerRejectingNilUUID>> violations = validator.validate(container);

        assertThat(violations)
                .containsOnlyPaths(pathWith().property("uuid"));
    }

    @Test
    void shouldNotRejectNilUUIDByDefault() {
        Container container = Container.of(NIL_UUID);

        assertThat(validator.validate(container)).isEmpty();
    }

    @Value(staticConstructor = "of")
    private static class ContainerRejectingNilUUID {
        @UUID(allowNil = false)
        String uuid;
    }

    @Value(staticConstructor = "of")
    private static class Container {
        @UUID
        String uuid;
    }
}