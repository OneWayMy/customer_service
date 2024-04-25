package com.clearsolution.user_service.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    @NotNull(message = "User first name cant be null")
    @NotBlank(message = "User first name cant be blank")
    private String firstName;
    @NotNull(message = "User last name cant be null")
    @NotBlank(message = "User last name cant be blank")
    private String lastName;
    @NotNull(message = "User email cant be null")
    @NotBlank(message = "User email cant be blank")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "User birth date cant be null")
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
