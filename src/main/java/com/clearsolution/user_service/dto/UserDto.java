package com.clearsolution.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime birthDate;
    private Optional<String> address;
    private Optional<String> phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
