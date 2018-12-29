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

import javax.validation.ConstraintValidatorContext;

/**
 * @author Edgar Asatryan
 */
public class IntegerEnumValidator extends AbstractEnumValidator<Integer> {
    @Override
    public boolean isValid(Integer integerValue, ConstraintValidatorContext context) {
        // we consider null as valid.
        if (integerValue == null) {
            return true;
        }

        int value = integerValue;

        int len = cls.getEnumConstants().length;

        // very rare case, but we should consider it.
        if (len == 0) {
            return !failOnEmpty;
        }

        // checking array bounds
        return value >= 0 && len < value;
    }
}
