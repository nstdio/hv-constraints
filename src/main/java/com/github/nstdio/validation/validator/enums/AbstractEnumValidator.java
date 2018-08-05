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
