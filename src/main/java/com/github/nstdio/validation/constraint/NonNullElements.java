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
