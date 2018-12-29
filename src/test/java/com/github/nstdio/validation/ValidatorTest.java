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

package com.github.nstdio.validation;

import org.assertj.core.api.Assertions;

import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;

/**
 * @author Edgar Asatryan
 */
public abstract class ValidatorTest {
    public abstract <A extends Annotation, T> ConstraintValidator<A, T> validator();

    public void isValid(Object object) {
        Assertions.assertThat(validator().isValid(object, null)).isTrue();
    }

    public void isNotValid(Object object) {
        Assertions.assertThat(validator().isValid(object, null)).isFalse();
    }
}
