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