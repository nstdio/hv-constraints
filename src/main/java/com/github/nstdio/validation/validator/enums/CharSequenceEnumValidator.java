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
