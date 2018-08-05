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
