package com.github.nstdio.validation.constraint;

import com.github.nstdio.validation.validator.enums.CharSequenceArrayEnumValidator;
import com.github.nstdio.validation.validator.enums.CharSequenceEnumValidator;
import com.github.nstdio.validation.validator.enums.IntegerEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a valid representation of Enum.
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@code CharSequence} (The {@link java.lang.Enum#name()} is evaluated)</li>
 * <li>{@code CharSequence[]} (The each element {@link java.lang.Enum#name()} is evaluated)</li>
 * <li>{@code int} (The {@link java.lang.Enum#ordinal()} is evaluated)</li>
 * <li>{@code Integer} (The {@link java.lang.Enum#ordinal()} is evaluated)</li>
 * </ul>
 * <p>
 * For the {@code CharSequence[]} the {@code null} values are permitted. To control acceptance of {@code null} values
 * please use {@link NonNullElements}.
 *
 * @author Edgar Asatryan
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {CharSequenceEnumValidator.class, CharSequenceArrayEnumValidator.class, IntegerEnumValidator.class})
public @interface Enum {

    /**
     * The message or message template to use when validation was failed.
     * <p>
     * For referencing all constants of {@link #value()} enum please use {@code {enums}} placeholder.
     * <p>
     * Here is the usage semantics:
     * <pre>
     *    enum Role {
     *        SYSTEM,
     *        ADMIN,
     *        USER,
     *        GUEST
     *    }
     *
     *    class Data {
     *        &#064;Enum(value = Role.class, message = "${validatedValue} must be one of {enums}")
     *        String value;
     *    }
     * </pre>
     * <p>
     * In this example the {@code {enums}} will be replaced with {@literal SYSTEM, ADMIN, USER, GUEST}.
     *
     * @return The message or message template to use when validation was failed.
     */
    String message() default "{com.github.nstdio.validation.constraint.Enum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * The {@code Class} object of the enum type.
     */
    Class<? extends java.lang.Enum<?>> value();

    /**
     * This property assumes that the validated value is {@code CharSequence}. Signals validator whether use case
     * sensitive comparision or not.
     *
     * @return Whether use case sensitive comparision or not.
     */
    boolean caseSensitive() default true;

    /**
     * There is valid to define enum without any constants. This property allows to control that rare case behavior.
     * When enum does not have any constants and this property is {@code true} validation should always fail. Otherwise
     * validation should always passed.
     * <p>
     * In other to optimize execution there is recommended to check validated value is valid Java identifier or not. The
     * enum constant name must be a valid Java identifier. That checking should take precedence over this flag. The only
     * case when validator can fail (regardless of this property) on empty enums is that validated value is illegal java
     * identifier any other value should take into account this flag.
     */
    boolean failOnEmpty() default true;
}
