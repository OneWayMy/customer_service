package com.clearsolution.user_service.service;

import com.clearsolution.user_service.ViewModels.UserVM;
import com.clearsolution.user_service.dto.UserRegistrationRequest;
import com.clearsolution.user_service.entity.User;
import com.clearsolution.user_service.mapper.UserMapper;
import com.clearsolution.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserVM CreateUser(UserRegistrationRequest registrationRequest){
        User user = userMapper.toEntity(registrationRequest);

        User savedUser = userRepository.save(user);
        log.info("User '{}' was successfully registered.", savedUser.getFirstName());

        return userMapper.toVM(savedUser);
    }
}