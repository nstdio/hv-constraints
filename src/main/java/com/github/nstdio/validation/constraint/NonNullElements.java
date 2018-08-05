package com.github.nstdio.validation.constraint;

import com.github.nstdio.validation.validator.nonnullelements.NonNullElementsForIterable;
import com.github.nstdio.validation.validator.nonnullelements.NonNullElementsForList;
import com.github.nstdio.validation.validator.nonnullelements.NonNullElementsValidatorForObjectArray;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must not contain the {@code null}s.
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@code Object[]}</li>
 * <li>{@code List}</li>
 * <li>{@code Iterable}</li>
 * </ul>
 * For index based container like arrays or lists there is message parameters available:
 *
 * <ul>
 * <li>{indices} - the comma separated indices where {@code null} was found</li>
 * <ul/>
 *
 * @author Edgar Asatryan
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {NonNullElementsValidatorForObjectArray.class, NonNullElementsForList.class, NonNullElementsForIterable.class})
public @interface NonNullElements {
    String message() default "{com.github.nstdio.validation.constraint.NonNullElements.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
