package com.clearsolution.user_service.controller;

import com.clearsolution.user_service.dto.UserDto;
import com.clearsolution.user_service.service.UserService;
import com.clearsolution.user_service.utils.ControllerValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final ControllerValidator validator;
    @PostMapping("/registration")
    public void CreateUser(@RequestBody @Valid UserDto userDto) {
        log.info("Received request to create a user");

        validator.validateUserRegistrationDetails(userDto.getBirthDate());
        log.info("User passed all required validations for registration");

        userService.CreateUser(userDto);
    }
}
