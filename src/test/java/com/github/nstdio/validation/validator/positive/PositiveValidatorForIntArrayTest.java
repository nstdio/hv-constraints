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

package com.github.nstdio.validation.validator.positive;

import com.github.nstdio.validation.ValidatorTest;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidator;
import javax.validation.constraints.Positive;

/**
 * @author Edgar Asatryan
 */
class PositiveValidatorForIntArrayTest extends ValidatorTest {
    private final PositiveValidatorForIntArray validator = new PositiveValidatorForIntArray();

    @Test
    void shouldBeValidWhenValidatedValueIsNull() {
        isValid(null);
    }

    @Test
    void shouldBeValidWhenValidatedValueIsEmpty() {
        isValid(new int[0]);
    }

    @Test
    void shouldBeValidWhenNoNegativeOrZeroValuesArePresent() {
        isValid(new int[]{1, 2, 3, 4});
    }

    @Test
    void shouldBeNotValidZeroPresent() {
        isNotValid(new int[]{1, 2, 3, 4, 0});
    }

    @Test
    void shouldBeNotValidNegativePresent() {
        isNotValid(new int[]{1, 2, 3, 4, -1});
    }

    @Override
    @SuppressWarnings("unchecked")
    public ConstraintValidator<Positive, int[]> validator() {
        return validator;
    }
}