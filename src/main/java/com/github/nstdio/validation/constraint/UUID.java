package com.github.nstdio.validation.constraint;

import com.github.nstdio.validation.validator.uuid.UUIDValidatorForCharSequence;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a valid representation of UUID.
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@code CharSequence}</li>
 * </ul>
 *
 * @author Edgar Asatryan
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {UUIDValidatorForCharSequence.class})
public @interface UUID {
    String message() default "{com.github.nstdio.validation.constraint.UUID.message}";

    /**
     * The configuration flag allowing to control validation so called {@code Nil UUID}.
     * <p>
     * The Nil UUID is: 00000000-0000-0000-0000-000000000000.
     * <p>
     * The default behavior is to accept the Nil UUID.
     *
     * @return Whether accept or reject Nil UUID.
     */
    boolean allowNil() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
