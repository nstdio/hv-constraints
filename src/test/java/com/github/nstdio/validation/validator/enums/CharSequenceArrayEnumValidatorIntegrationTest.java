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
import com.google.common.collect.Iterables;
import lombok.Value;
import org.hibernate.validator.engine.HibernateConstraintViolation;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Edgar Asatryan
 */
class CharSequenceArrayEnumValidatorIntegrationTest extends ValidatorIntegrationTest {

    @Test
    void should() {
        Container container = Container.of(new String[]{"a", "b", "c"});

        ConstraintViolation<Container> violation = Iterables.getOnlyElement(validator.validate(container));

        assertThat(violation.getMessage())
                .endsWith("[SYSTEM, DEVELOPER, MASTER_ADMIN, PLAIN_ADMIN, USER, GUEST, TEMP]");
    }

    @Test
    void validatedValueExpressionVariableShouldBeReplaced() {
        Container container = Container.of(new String[]{"abc", "def"});

        ConstraintViolation<Container> violation = Iterables.getOnlyElement(validator.validate(container));

        assertThat(violation.getMessage()).startsWith("[abc, def]");
    }

    @Test
    void dynamicPayloadShouldBeEqualToEnumConstants() {
        Container container = Container.of(new String[]{"a"});
        ConstraintViolation<Container> violation = Iterables.getOnlyElement(validator.validate(container));

        Object[] payload = (Object[]) violation.unwrap(HibernateConstraintViolation.class).getDynamicPayload(Object[].class);

        assertThat(payload).isEqualTo(Enums.RoleUpperCase.values());
    }

    @Value(staticConstructor = "of")
    private static class Container {
        @Enum(value = Enums.RoleUpperCase.class, message = "[${validatedValue}] must fully intersect with [{enums}]")
        CharSequence[] roles;
    }
}