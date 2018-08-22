package com.github.nstdio.validation.constraint;

import com.github.nstdio.validation.validator.uuid.UUIDValidatorForString;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Edgar Asatryan
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {UUIDValidatorForString.class})
public @interface UUID {
    String message() default "{com.github.nstdio.validation.constraint.UUID.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
