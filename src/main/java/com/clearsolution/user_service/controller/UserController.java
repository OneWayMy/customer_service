package com.clearsolution.user_service.controller;

import com.clearsolution.user_service.dto.UserDto;
import com.clearsolution.user_service.service.UserService;
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
    @PostMapping("/create")
    public void CreateUser(@RequestBody UserDto userDto) {
        log.info("Received request to create a user");
        userService.CreateUser(userDto);
    }
}
