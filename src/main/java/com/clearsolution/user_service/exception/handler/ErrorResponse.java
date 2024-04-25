package com.clearsolution.user_service.exception.handler;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final String message;
    private LocalDateTime errorTimestamp = LocalDateTime.now();
}
