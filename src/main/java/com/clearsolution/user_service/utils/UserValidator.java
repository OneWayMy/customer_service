package com.clearsolution.user_service.utils;

import com.clearsolution.user_service.exception.DataValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class UserValidator {

    @Value("${registration.user.min_age}")
    private int MIN_REGISTRATION_USER_AGE;

    public void validateUserBirthAndAge(LocalDate userBirthDate){
        validateUserBirthDate(userBirthDate);
        validateUserAge(userBirthDate);
    }

    public void validateUserAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();

        int userAge = Period.between(birthDate, currentDate)
                .getYears();

        if (userAge <= MIN_REGISTRATION_USER_AGE) {
            throw new DataValidationException(String.format("User must be at least %d years old to register.",
                    MIN_REGISTRATION_USER_AGE));
        }
    }

    public void validateUserBirthDate(LocalDate birthdate) {
        LocalDate currentTime = LocalDate.now();

        if (birthdate.isEqual(currentTime) || birthdate.isAfter(currentTime)) {
            throw new DataValidationException("Birth date must be earlier than current date");
        }
    }
}
