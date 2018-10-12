package com.github.nstdio.validation.validator.uuid;

import com.github.nstdio.validation.ValidatorIntegrationTest;
import com.github.nstdio.validation.constraint.UUID;
import lombok.Value;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.hibernate.validator.testutil.ConstraintViolationAssert.assertThat;
import static org.hibernate.validator.testutil.ConstraintViolationAssert.pathWith;

/**
 * @author Edgar Asatryan
 */
class UUIDValidatorForCharSequenceIntegrationTest extends ValidatorIntegrationTest {
    private static final String NIL_UUID = "00000000-0000-0000-0000-000000000000";

    @Test
    void shouldRejectNilUUID() {
        ContainerRejectingNilUUID container = ContainerRejectingNilUUID.of(NIL_UUID);
        Set<ConstraintViolation<ContainerRejectingNilUUID>> violations = validator.validate(container);

        assertThat(violations)
                .containsOnlyPaths(pathWith().property("uuid"));
    }

    @Test
    void shouldNotRejectNilUUIDByDefault() {
        Container container = Container.of(NIL_UUID);

        assertThat(validator.validate(container)).isEmpty();
    }

    @Value(staticConstructor = "of")
    private static class ContainerRejectingNilUUID {
        @UUID(allowNil = false)
        String uuid;
    }

    @Value(staticConstructor = "of")
    private static class Container {
        @UUID
        String uuid;
    }
}