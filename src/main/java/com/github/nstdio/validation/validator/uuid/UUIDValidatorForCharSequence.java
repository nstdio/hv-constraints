/*
 * Copyright 2018 Edgar Asatryan <nstdio@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.nstdio.validation.validator.uuid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.nstdio.validation.constraint.UUID;

/**
 * @author Edgar Asatryan
 */
public class UUIDValidatorForCharSequence implements ConstraintValidator<UUID, CharSequence> {
    static final int UUID_LENGTH = 36;
    private static final char DELIM = '-';
    private static final String NIL_UUID = "00000000-0000-0000-0000-000000000000";

    private boolean allowNil = true;

    private static boolean isHex(char c) {
        return (c >= 'a' && c <= 'f') || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c == DELIM);
    }

    static boolean doValidation(CharSequence value, boolean allowNil) {
        if (value == null) {
            return true;
        }

        final int length = value.length();

        // UUID must be exactly 36 hex characters.
        if (length != UUID_LENGTH) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            switch (i) {
                case 8:
                case 13:
                case 18:
                case 23:
                    if (value.charAt(i) != DELIM) {
                        return false;
                    }
                    break;
                default:
                    if (!isHex(value.charAt(i))) {
                        return false;
                    }
            }
        }

        // we don't allow Nil UUID.
        if (!allowNil) {
            return !NIL_UUID.contentEquals(value);
        }

        return true;
    }

    @Override
    public void initialize(UUID constraintAnnotation) {
        allowNil = constraintAnnotation.allowNil();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return doValidation(value, allowNil);
    }
}
