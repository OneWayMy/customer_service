package com.clearsolution.user_service.ViewModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVM {
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
