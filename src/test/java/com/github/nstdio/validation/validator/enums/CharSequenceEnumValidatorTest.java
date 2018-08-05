package com.github.nstdio.validation.validator.enums;

import com.github.nstdio.validation.AnnotationFactory;
import com.github.nstdio.validation.Enums.Empty;
import com.github.nstdio.validation.validator.enums.CharSequenceEnumValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Edgar Asatryan
 */
class CharSequenceEnumValidatorTest {

    private CharSequenceEnumValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CharSequenceEnumValidator();
    }

    private void isValid(String value) {
        assertThat(validator.isValid(value, null)).isTrue();
    }

    private void isNotValid(String value) {
        assertThat(validator.isValid(value, null)).isFalse();
    }

    private enum TestEnum1 {
        A,
        B,
        C
    }

    private enum NonAsciiEnum {
        ՄԵԿ,
        ԵՐԿՈՒ,
        ԵՐԵՔ
    }

    @Nested
    class Valid {
        @Test
        void nullIsValidCaseSensitive() {
            validator.initialize(AnnotationFactory.enumAnnotation(TestEnum1.class));

            isValid(null);
        }

        @Test
        void nullIsValidCaseInsensitive() {
            validator.initialize(AnnotationFactory.enumAnnotation(TestEnum1.class, false));

            isValid(null);
        }

        @Test
        void whenCaseSensitive() {
            validator.initialize(AnnotationFactory.enumAnnotation(TestEnum1.class));

            isValid("A");
            isValid("B");
            isValid("C");
        }

        @Test
        void whenCaseInsensitive() {
            validator.initialize(AnnotationFactory.enumAnnotation(TestEnum1.class, false));

            isValid("a");
            isValid("b");
            isValid("c");
        }

        @Test
        void whenNonAsciiEnumCaseSensitive() {
            validator.initialize(AnnotationFactory.enumAnnotation(NonAsciiEnum.class));

            isValid("ՄԵԿ");
            isValid("ԵՐԿՈՒ");
            isValid("ԵՐԵՔ");
        }

        @Test
        void whenNonAsciiEnumCaseInsensitive() {
            validator.initialize(AnnotationFactory.enumAnnotation(NonAsciiEnum.class, false));

            isValid("մեկ");
            isValid("երկու");
            isValid("երեք");
        }

        @Test
        void whenEnumIsEmptyAndFailOnEmptyIsFalse() {
            validator.initialize(AnnotationFactory.enumAnnotation(Empty.class, true, false));

            isValid("any");
            isValid("anyOther");
        }
    }

    @Nested
    class NotValid {
        @Test
        void notValidWhenCaseSensitive() {
            validator.initialize(AnnotationFactory.enumAnnotation(TestEnum1.class));

            isNotValid("a");
            isNotValid("b");
            isNotValid("c");
        }

        @Test
        void whenValueIsNotJavaIdentifier() {
            validator.initialize(AnnotationFactory.enumAnnotation(TestEnum1.class));

            isNotValid("");
            isNotValid(" ");
            isNotValid("\n");
        }

        @Test
        void whenEnumIsEmptyAndFailOnEmptyIsTrue() {
            validator.initialize(AnnotationFactory.enumAnnotation(Empty.class, true, true));

            isNotValid("any");
            isNotValid("anyOther");
        }

        @Test
        void whenEnumIsEmptyAndFailOnIsTrueAndValueIsNotJavaIdentifier() {
            validator.initialize(AnnotationFactory.enumAnnotation(Empty.class, true, true));

            isNotValid("-any");
            isNotValid("#any");
            isNotValid("");
            isNotValid("1abc");
        }
    }
}