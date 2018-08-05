package com.github.nstdio.validation.validator.enums;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidatorContext;

/**
 * @author Edgar Asatryan
 */
public class CharSequenceEnumValidator extends AbstractEnumValidator<CharSequence> {
    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        // we consider null as valid.
        if (value == null) {
            return true;
        }

        // We should fail fast here.
        // According to jls-8.9.1 enum constant name cannot be an invalid java identifier.
        if (isNotValidJavaIdentifier().test(value)) {
            return false;
        }

        Enum<?>[] enums = cls.getEnumConstants();

        // very rare case, but we should consider it.
        if (enums.length == 0) {
            return !failOnEmpty;
        }

        boolean isValid = toCharSequenceStream()
                .apply(enums)
                .anyMatch(cs -> caseSensitive ? cs.equals(value) : ((String) cs).equalsIgnoreCase((String) value));

        if (!isValid) {
            if (context instanceof HibernateConstraintValidatorContext) {
                context.unwrap(HibernateConstraintValidatorContext.class)
                        .addMessageParameter("enums", join().apply(enums))
                        .withDynamicPayload(enums);
            }
        }

        return isValid;
    }
}
