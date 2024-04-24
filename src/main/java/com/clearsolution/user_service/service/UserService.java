package com.clearsolution.user_service.service;

import com.clearsolution.user_service.dto.UserDto;
import com.clearsolution.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void CreateUser(UserDto userDto){

    }
}