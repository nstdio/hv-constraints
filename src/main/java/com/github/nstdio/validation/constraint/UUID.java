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

package com.github.nstdio.validation.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.github.nstdio.validation.validator.uuid.UUIDValidatorForCharArray;
import com.github.nstdio.validation.validator.uuid.UUIDValidatorForCharSequence;

/**
 * The annotated element must be a valid representation of UUID.
 * <p>
 * Supported types are:
 * <ul>
 * <li>{@code CharSequence}</li>
 * <li>{@code char[]}</li>
 * </ul>
 *
 * @author Edgar Asatryan
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {UUIDValidatorForCharSequence.class, UUIDValidatorForCharArray.class})
public @interface UUID {
    String message() default "{com.github.nstdio.validation.constraint.UUID.message}";

    /**
     * The configuration flag allowing to control validation so called {@code Nil UUID}.
     * <p>
     * The Nil UUID is: 00000000-0000-0000-0000-000000000000.
     * <p>
     * The default behavior is to accept the Nil UUID.
     *
     * @return Whether accept or reject Nil UUID.
     */
    boolean allowNil() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
