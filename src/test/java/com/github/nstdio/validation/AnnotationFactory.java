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
