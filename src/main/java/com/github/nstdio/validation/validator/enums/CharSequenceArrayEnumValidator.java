package com.github.nstdio.validation.validator.enums;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.engine.HibernateConstraintViolation;

import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * The {@link com.github.nstdio.validation.constraint.Enum} validator for character sequence array. If validated value
 * satisfies (evaluates true) the following conditions {@code value == null || value.length == 0} then this validator
 * will consider it valid.
 * <p>
 * All elements of validated value should be valid to pass validation. In other words required enum should contain all
 * elements of validated value to pass validation.
 * <p>
 * Because the expression variable {@code ${validatedValue}} interpolates to default object {@code toString}
 * implementation this validator replace its value. For example if we keep things as is the message containing {@code
 * ${validatedValue}} will evaluate to something like this:
 * <pre>
 * // ...
 * &#064;Enum(value = SomeEnum.class, message = "${validatedValue} is not valid.")
 * String[] values;
 * // ...
 * <pre/>
 * The message will be something like this: {@literal [Ljava.lang.String;@5a59ca5e is not valid}. In other to change
 * that behavior validator replace expression variable to comma separated array elements. The example above will
 * evaluate to this: {@literal A, B, C is not valid} where validated value is {@code new String[]{"A", "B", "C"}}.
 *
 * Expression variables and message parameters available:
 * <ul>
 * <li>${validatedValue} - the comma separated validated value elements</li>
 * <li>{enums} - the comma separated enum elements</li>
 * </ul>
 *
 * As en addition the all enum constants for enum type {@link com.github.nstdio.validation.constraint.Enum#value()} will be passed as constraint violation
 * dynamic payload. It can be retrieved {@link HibernateConstraintViolation#getDynamicPayload(Class)}.
 *
 * @author Edgar Asatryan
 */
public class CharSequenceArrayEnumValidator extends AbstractEnumValidator<CharSequence[]> {
    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(CharSequence[] value, ConstraintValidatorContext context) {
        // we consider nulls and empty arrays as valid.
        if (value == null || value.length == 0) {
            return true;
        }

        boolean hasNonValidJavaIdentifiers = Stream.of(value).anyMatch(isNotValidJavaIdentifier());

        Enum<?>[] enums = cls.getEnumConstants();

        if (hasNonValidJavaIdentifiers) {
            addVariables(value, context, enums);
            return false;
        }

        if (enums.length == 0) {
            return !failOnEmpty;
        }

        Function<CharSequence, String> toStringFunc = caseSensitive ? CharSequence::toString : cs -> cs.toString().toUpperCase();

        Set<String> names = toCharSequenceStream()
                .apply(enums)
                .map(toStringFunc)
                .collect(toSet());

        // Doest names include all values ?
        boolean mismatch = Arrays.stream(value)
                .filter(Objects::nonNull)
                .map(toStringFunc)
                // do not use all match to break stream as soon as possible
                .anyMatch(s -> !names.contains(s));

        boolean isValid = !mismatch;

        if (!isValid) {
            addVariables(value, context, enums);
        }

        return isValid;
    }

    private void addVariables(CharSequence[] value, ConstraintValidatorContext context, Enum<?>[] enums) {
        try {
            if (context instanceof HibernateConstraintValidatorContext) {
                context.unwrap(HibernateConstraintValidatorContext.class)
                        .addExpressionVariable("validatedValue", arrayToString().apply(value))
                        .addMessageParameter("enums", join().apply(enums))
                        .withDynamicPayload(enums);
            }
        } catch (NoClassDefFoundError ignored) {
        }
    }
}
