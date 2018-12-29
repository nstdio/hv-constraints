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

package com.github.nstdio.validation;

import com.github.nstdio.validation.constraint.Enum;
import com.google.common.collect.ImmutableMap;
import org.hibernate.validator.internal.util.annotation.AnnotationDescriptor;

/**
 * @author Edgar Asatryan
 */
public final class AnnotationFactory {
    private AnnotationFactory() {
    }

    public static Enum enumAnnotation(Class<? extends java.lang.Enum<?>> cls) {
        return enumAnnotation(cls, true);
    }

    public static Enum enumAnnotation(Class<? extends java.lang.Enum<?>> cls, boolean caseSensitive) {
        return enumAnnotation(cls, caseSensitive, true);
    }

    public static Enum enumAnnotation(Class<? extends java.lang.Enum<?>> cls, boolean caseSensitive, boolean failOnEmpty) {
        return org.hibernate.validator.internal.util.annotation.AnnotationFactory.create(
                new AnnotationDescriptor.Builder<>(Enum.class,
                        ImmutableMap.of(
                                "value", cls,
                                "caseSensitive", caseSensitive,
                                "failOnEmpty", failOnEmpty
                        )
                )
                        .build()
        );
    }
}
