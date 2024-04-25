package com.clearsolution.user_service.controller;

import com.clearsolution.user_service.ViewModels.UserVM;
import com.clearsolution.user_service.request.UserCreateRequest;
import com.clearsolution.user_service.request.UserUpdateRequest;
import com.clearsolution.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user based on the provided registration request"
    )
    public ResponseEntity<UserVM> createUser(@RequestBody @Valid UserCreateRequest registrationRequest) {
        log.info("Received POST request to create a user");
        UserVM createdUser = userService.createUser(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PatchMapping("/{userId}")
    @Operation(
            summary = "Update one or more user fields",
            description = "Partially updates the specified fields of the user."
    )
    public ResponseEntity<UserVM> updateUserPartial(
            @PathVariable long userId,
            @RequestBody @Valid UserUpdateRequest updateRequest
    ) {
        log.info("Received PATCH request to update one/some user fields");
        UserVM updatedUser = userService.updateUserInfo(userId, updateRequest, true);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PutMapping("/{userId}")
    @Operation(
            summary = "Update all user fields",
            description = "Updates all fields of the user based on the provided request."
    )
    public ResponseEntity<UserVM> updateAllUserFields(
            @PathVariable long userId,
            @RequestBody @Valid UserUpdateRequest updateRequest
    ) {
        log.info("Received PUT request to update all user fields");
        UserVM updatedUser = userService.updateUserInfo(userId, updateRequest, false);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(
            summary = "Delete a user",
            description = "Deletes a user by their ID."
    )
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        log.info("Received DELETE request to delete User with ID: {}", userId);
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search for users by birth date range",
            description = "Retrieves a list of users whose birth date falls within the specified range."
    )
    public ResponseEntity<List<UserVM>> getUsersByDate(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate
    ) {
        log.info("Received GET request to retrieve users by ages from {} to a {}", fromDate, toDate);
        List<UserVM> usersInDateRange = userService.getUsersByDate(fromDate, toDate);
        return ResponseEntity.status(HttpStatus.OK).body(usersInDateRange);
    }
}