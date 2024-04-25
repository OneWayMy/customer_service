package com.clearsolution.user_service.service;

import com.clearsolution.user_service.ViewModels.UserVM;
import com.clearsolution.user_service.exception.DataValidationException;
import com.clearsolution.user_service.request.UserUpdateRequest;
import com.clearsolution.user_service.request.UserCreateRequest;
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
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator validator;

    public UserVM createUser(UserCreateRequest registrationRequest) {
        validator.validateUserBirthAndAge(registrationRequest.getBirthDate());

        User user = userMapper.toEntity(registrationRequest);

        User savedUser = userRepository.save(user);
        log.info("User '{}' was successfully created.", savedUser.getFirstName());

        return userMapper.toVM(savedUser);
    }

    @Transactional
    public UserVM updateUserInfo(long userId, UserUpdateRequest updateRequest, boolean isPartialUpdate) {
        LocalDate userBirthDate = updateRequest.getBirthDate();

        log.info("Starting update for user with ID {}. Partial update: {}", userId, isPartialUpdate);

        if (userBirthDate != null) {
            validator.validateUserBirthAndAge(userBirthDate);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with ID %d doesn't exist", userId)));

        if (isPartialUpdate) {
            updatePartialUserFields(user, updateRequest);
        } else {
            updateAllUserFields(user, updateRequest);
        }

        log.info("Successfully updated user with ID {}. Partial update: {}", userId, isPartialUpdate);

        return userMapper.toVM(user);
    }

    public void deleteUserById(long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserVM> getUsersByDate(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate)) {
            throw new DataValidationException("'From' date cannot be after 'to' date.");
        }

        List<User> users = userRepository.findByBirthDateRange(fromDate, toDate);
        log.info("Found {} users with birth dates in range: from {} to {}", users.size(), fromDate, toDate);

        return users.stream()
                .map(userMapper::toVM)
                .toList();
    }

    private void updatePartialUserFields(User user, UserUpdateRequest updateRequest) {
        user.updatePersonalInfo(updateRequest.getFirstName(), updateRequest.getLastName(), updateRequest.getBirthDate());
        user.updateContactInfo(updateRequest.getPhoneNumber(), updateRequest.getEmail(), updateRequest.getAddress());
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
    }
}