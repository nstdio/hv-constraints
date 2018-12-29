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

import com.github.nstdio.validation.constraint.Enum;

import javax.validation.ConstraintValidator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Edgar Asatryan
 */
abstract class AbstractEnumValidator<T> implements ConstraintValidator<Enum, T> {
    private static final String DELIMITER = ", ";

    Class<? extends java.lang.Enum<?>> cls;
    boolean caseSensitive;
    boolean failOnEmpty;

    @Override
    public void initialize(Enum annotation) {
        cls = annotation.value();
        caseSensitive = annotation.caseSensitive();
        failOnEmpty = annotation.failOnEmpty();
    }

    Predicate<CharSequence> isNotValidJavaIdentifier() {
        return cs -> {
            if (cs == null) {
                return false;
            }

            return cs.length() == 0 || !Character.isJavaIdentifierStart(cs.charAt(0));
        };
    }

    Function<java.lang.Enum<?>[], Stream<CharSequence>> toCharSequenceStream() {
        return enums -> Stream.of(enums)
                .map(java.lang.Enum::name)
                .map(CharSequence.class::cast);
    }

    Function<java.lang.Enum<?>[], CharSequence> join() {
        return enums -> toCharSequenceStream()
                .apply(enums)
                .collect(Collectors.joining(DELIMITER));
    }

    <A> Function<A[], CharSequence> arrayToString() {
        return ts -> Stream.of(ts)
                .map(Objects::toString)
                .collect(Collectors.joining(DELIMITER));
    }
}
