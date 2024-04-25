package com.clearsolution.user_service.util;

import com.clearsolution.user_service.exception.DataValidationException;
import com.clearsolution.user_service.utils.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

public class UserValidatorTest {

    UserValidator userValidator = new UserValidator();
    private final int MIN_REGISTRATION_USER_AGE = 18;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(userValidator, "MIN_REGISTRATION_USER_AGE", MIN_REGISTRATION_USER_AGE);
    }

    @Test
    public void validateUserAgeTest() {
        LocalDate birthDate = LocalDate.of(2020, 1, 1);

        DataValidationException exception =
                Assertions.assertThrows(DataValidationException.class, () -> userValidator.validateUserAge(birthDate));

        String expectedMessage = String.format("User must be at least %s years old to register.", MIN_REGISTRATION_USER_AGE);
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void validateUserBirthDateTest() {
        LocalDate birthDate = LocalDate.of(4000, 1, 1);

        DataValidationException exception =
                Assertions.assertThrows(DataValidationException.class, () -> userValidator.validateUserBirthAndAge(birthDate));

        Assertions.assertEquals("Birth date must be earlier than current date", exception.getMessage());
    }
}
