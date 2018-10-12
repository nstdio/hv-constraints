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

    /**
     * The versions to check.
     *
     * @return The valid versions for validating UUID's.
     */
    int[] versions() default {Version.ALL};

    /**
     * The configuration flag allowing to control validation so called {@code Nil UUID}.
     * <p>
     * The Nil UUID is: 00000000-0000-0000-0000-000000000000.
     * <p>
     * The default behavior is to accept the Nil UUID.
     *
     * @return Whether accept or reject Nil UUID.
     */
    boolean acceptNil() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * The UUID version.
     */
    interface Version {
        /**
         * All versions.
         */
        int ALL = -1;

        /**
         * Time-based UUID's.
         */
        int TIME = 1;

        /**
         * DCE
         */
        int DCE = 2;

        /**
         * Name-based UUID's.
         */
        int NAME = 3;

        /**
         * Randomly generated UUID's.
         */
        int RANDOM = 4;
    }
}
