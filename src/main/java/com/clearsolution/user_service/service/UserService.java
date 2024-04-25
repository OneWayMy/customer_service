package com.clearsolution.user_service.service;

import com.clearsolution.user_service.ViewModels.UserVM;
import com.clearsolution.user_service.dto.UserUpdateRequest;
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
        validator.validateUserBirthAndAge(registrationRequest.getBirthDate());
        log.info("User passed all required validations for registration");

        User user = userMapper.toEntity(registrationRequest);

        User savedUser = userRepository.save(user);
        log.info("User '{}' was successfully registered.", savedUser.getFirstName());

        return userMapper.toVM(savedUser);
    }

    @Transactional
    public UserVM updateUserInfo(UserUpdateRequest updateRequest, boolean isPartialUpdate) {
        long userId = updateRequest.getUserId();
        LocalDate userBirthDate = updateRequest.getBirthDate();

        if (userBirthDate != null) {
            validator.validateUserBirthAndAge(userBirthDate);
        }

        log.info("User with ID {} has passed validation for updates.", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d doesn't exist", userId)));

        if (isPartialUpdate) {
            updatePartialUserFields(user, updateRequest);
        } else {
            updateAllUserFields(user, updateRequest);
        }

        user.setUpdatedAt(LocalDateTime.now());

        return userMapper.toVM(user);
    }

    public void deleteUserById(long userId) {
        userRepository.deleteById(userId);
    }

    private void updatePartialUserFields(User user, UserUpdateRequest updateRequest) {
        user.updatePersonalInfo(updateRequest.getFirstName(), updateRequest.getLastName(), updateRequest.getBirthDate());
        user.updateContactInfo(updateRequest.getPhoneNumber(), updateRequest.getEmail(), updateRequest.getAddress());

        log.info("User with ID {} was successfully updated partial fields.", updateRequest.getUserId());
    }

    private void updateAllUserFields(User user, UserUpdateRequest updateRequest) {
        user.updateInfo(
                updateRequest.getFirstName(),
                updateRequest.getLastName(),
                updateRequest.getBirthDate(),
                updateRequest.getPhoneNumber(),
                updateRequest.getEmail(),
                updateRequest.getAddress()
        );

        log.info("User with ID {} was successfully updated all his fields.", updateRequest.getUserId());
    }
}