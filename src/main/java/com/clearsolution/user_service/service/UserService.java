package com.clearsolution.user_service.service;

import com.clearsolution.user_service.ViewModels.UserVM;
import com.clearsolution.user_service.dto.UserFieldsUpdateRequest;
import com.clearsolution.user_service.dto.UserRegistrationRequest;
import com.clearsolution.user_service.entity.User;
import com.clearsolution.user_service.mapper.UserMapper;
import com.clearsolution.user_service.repository.UserRepository;
import com.clearsolution.user_service.utils.UserValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator validator;

    public UserVM createUser(UserRegistrationRequest registrationRequest) {
        validator.validateUserRegistrationDetails(registrationRequest.getBirthDate());
        log.info("User passed all required validations for registration");

        User user = userMapper.toEntity(registrationRequest);

        User savedUser = userRepository.save(user);
        log.info("User '{}' was successfully registered.", savedUser.getFirstName());

        return userMapper.toVM(savedUser);
    }

    @Transactional
    public UserVM updateUserPartial(UserFieldsUpdateRequest updateRequest) {
        long userId = updateRequest.getUserId();
        LocalDate userBirthDate = updateRequest.getBirthDate();

        if (userBirthDate != null) {
            validator.validateUserBirthDate(userBirthDate);
            validator.validateUserAge(userBirthDate);
        }

        log.info("User with ID {} has passed validation for updates.", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d doesn't exist", userId)));

        user.updatePersonalInfo(updateRequest.getFirstName(), updateRequest.getLastName(), userBirthDate);
        user.updateContactInfo(updateRequest.getPhoneNumber(), updateRequest.getEmail(), updateRequest.getAddress());
        log.info("User with ID {} was successfully updated.",userId);

        user.setUpdatedAt(LocalDateTime.now());

        return userMapper.toVM(user);
    }
}