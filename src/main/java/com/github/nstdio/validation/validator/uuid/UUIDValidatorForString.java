package com.github.nstdio.validation.validator.uuid;

import com.github.nstdio.validation.constraint.UUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Edgar Asatryan
 */
public class UUIDValidatorForString implements ConstraintValidator<UUID, String> {
    private static final char DELIM = '-';
    private static final int UUID_LENGTH = 36;
    private static final String NIL_UUID = "00000000-0000-0000-0000-000000000000";

    private boolean acceptNil = true;

    private static boolean isHex(char c) {
        return (c >= 'a' && c <= 'f') || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c == DELIM);
    }

    @Override
    public void initialize(UUID constraintAnnotation) {
        acceptNil = constraintAnnotation.acceptNil();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        int length = value.length();

        // UUID must be exactly 36 hex characters.
        if (length != UUID_LENGTH)
            return false;

        for (int i = 0; i < length; i++) {
            switch (i) {
                case 8:
                case 13:
                case 18:
                case 23:
                    if (value.charAt(i) != DELIM)
                        return false;
                    break;
                default:
                    if (!isHex(value.charAt(i)))
                        return false;
            }
        }

        return acceptNil || NIL_UUID.equals(value);
    }
}
