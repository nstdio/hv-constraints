/*
 * Copyright 2019 Edgar Asatryan <nstdio@gmail.com>
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

import java.nio.CharBuffer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.nstdio.validation.constraint.UUID;

/**
 * @author Edgar Asatryan
 */
public class UUIDValidatorForCharArray implements ConstraintValidator<UUID, char[]> {
    private boolean allowNil;

    @Override
    public void initialize(UUID constraintAnnotation) {
        allowNil = constraintAnnotation.allowNil();
    }

    @Override
    public boolean isValid(char[] value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.length != UUIDValidatorForCharSequence.UUID_LENGTH) {
            return false;
        }

        return UUIDValidatorForCharSequence.doValidation(CharBuffer.wrap(value), allowNil);
    }
}
