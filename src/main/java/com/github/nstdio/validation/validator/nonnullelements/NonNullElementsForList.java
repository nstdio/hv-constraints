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

package com.github.nstdio.validation.validator.nonnullelements;

import com.github.nstdio.validation.constraint.NonNullElements;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Edgar Asatryan
 */
public class NonNullElementsForList implements ConstraintValidator<NonNullElements, List> {
    static boolean doValidation(List value, ConstraintValidatorContext context) {
        List<Integer> indices = new ArrayList<>();

        int size = value.size();
        for (int i = 0; i < size; i++) {
            if (value.get(i) == null)
                indices.add(i);
        }

        if (indices.isEmpty())
            return true;

        if (context instanceof HibernateConstraintValidatorContext) {
            context.unwrap(HibernateConstraintValidatorContext.class)
                    .addMessageParameter("indices", indices.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(", "))
                    )
                    .withDynamicPayload(Collections.unmodifiableList(indices));
        }

        return false;
    }

    @Override
    public boolean isValid(List value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return doValidation(value, context);
    }
}
