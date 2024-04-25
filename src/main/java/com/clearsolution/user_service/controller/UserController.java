package com.clearsolution.user_service.controller;

import com.clearsolution.user_service.ViewModels.UserVM;
import com.clearsolution.user_service.dto.UserUpdateRequest;
import com.clearsolution.user_service.dto.UserRegistrationRequest;
import com.clearsolution.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    @PostMapping("/registration")
    public UserVM createUser(@RequestBody @Valid UserRegistrationRequest registrationRequest) {
        log.info("Received request to create a user");
        return userService.createUser(registrationRequest);
    }

    @PatchMapping("/update/field")
    public UserVM updateUserPartial(@RequestBody @Valid UserUpdateRequest updateRequest) {
        log.info("Received request to update one/some user fields");
        return userService.updateUserInfo(updateRequest, true);
    }

    @PutMapping("/update/fields")
    public UserVM updateAllUserFields(@RequestBody @Valid UserUpdateRequest updateRequest) {
        log.info("Received request to update all user fields");
        return userService.updateUserInfo(updateRequest, false);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("Received request to delete User with ID: {}", userId);
        userService.deleteUserById(userId);
    }
}
