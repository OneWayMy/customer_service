package com.clearsolution.user_service.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFieldsUpdateRequest {
    private long userId;
    private String firstName;
    private String lastName;
    @Email(message = "Invalid email format")
    private String email;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
