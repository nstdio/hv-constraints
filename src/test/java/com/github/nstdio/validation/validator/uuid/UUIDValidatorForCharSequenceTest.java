package com.github.nstdio.validation.validator.uuid;

import com.github.nstdio.validation.ValidatorTest;
import com.github.nstdio.validation.constraint.UUID;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintValidator;
import java.util.stream.Stream;

/**
 * @author Edgar Asatryan
 */
class UUIDValidatorForCharSequenceTest extends ValidatorTest {
    private static Stream<String> valid() {
        return Stream.of(
                null,
                "fde109f0-9f9b-44ab-a7bc-6de53a4ee7cd",
                "FDE109F0-9F9B-44AB-A7BC-6DE53A4EE7CD"
        );
    }

    private static Stream<String> notValid() {
        return Stream.of(
                "",
                "abc",
                "zde109f0-9f9b-44ab-a7bc-6de53a4ee7cd",
                "fde109f09f9b44aba7bc6de53a4ee7cd"
        );
    }

    @ParameterizedTest
    @MethodSource("valid")
    void validUUIDs(String uuid) {
        isValid(uuid);
    }

    @ParameterizedTest
    @MethodSource("notValid")
    void notValidUUIDs(String notValid) {
        isNotValid(notValid);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ConstraintValidator<UUID, CharSequence> validator() {
        return new UUIDValidatorForCharSequence();
    }
}