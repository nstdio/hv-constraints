package com.github.nstdio.validation;

import org.junit.jupiter.api.BeforeAll;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author Edgar Asatryan
 */
public abstract class ValidatorIntegrationTest {
    protected static Validator validator;

    @BeforeAll
    static void setUpClass() {
        ValidatorFactory validatorFactory = Validation.byDefaultProvider()
                .configure()
                .buildValidatorFactory();

        validator = validatorFactory.getValidator();
    }
}
