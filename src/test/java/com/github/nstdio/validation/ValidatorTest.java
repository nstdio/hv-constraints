package com.github.nstdio.validation;

import org.assertj.core.api.Assertions;

import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;

/**
 * @author Edgar Asatryan
 */
public abstract class ValidatorTest {
    public abstract <A extends Annotation, T> ConstraintValidator<A, T> validator();

    public void isValid(Object object) {
        Assertions.assertThat(validator().isValid(object, null)).isTrue();
    }

    public void isNotValid(Object object) {
        Assertions.assertThat(validator().isValid(object, null)).isFalse();
    }
}
