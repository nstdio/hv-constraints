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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Edgar Asatryan
 */
class NonNullElementsForIterableTest {
    NonNullElementsForIterable validator = new NonNullElementsForIterable();

    @Test
    void shouldBeValidWhenIterableIsNull() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    void shouldBeValidWhenIterableIsEmpty() {
        assertTrue(validator.isValid(new ArrayList<>(), null));
        assertTrue(validator.isValid(Collections.emptyList(), null));
        assertTrue(validator.isValid(Collections.emptyNavigableSet(), null));
        assertTrue(validator.isValid(Collections.emptySet(), null));
        assertTrue(validator.isValid(ImmutableList.of(), null));
        assertTrue(validator.isValid(ImmutableSet.of(), null));
    }

    @Test
    void shouldBeNotValidWhenIterableContainNull() {
        ArrayList<String> list = Lists.newArrayList("a", null);

        assertFalse(validator.isValid(list, null));
    }
}