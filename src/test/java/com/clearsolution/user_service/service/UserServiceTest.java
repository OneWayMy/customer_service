package com.clearsolution.user_service.service;

import com.clearsolution.user_service.ViewModels.UserVM;
import com.clearsolution.user_service.dto.UserRegistrationRequest;
import com.clearsolution.user_service.entity.User;
import com.clearsolution.user_service.mapper.UserMapperImpl;
import com.clearsolution.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapperImpl userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void testGetUsersByIds() {
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .phoneNumber("123-456-7890")
                .build();

        User entity = userMapper.toEntity(request);

        User savedEntity = userMapper.toEntity(request);
        savedEntity.setId(1);

        Mockito.when(userRepository.save(entity)).thenReturn(savedEntity);

        UserVM result = userService.CreateUser(request);

        verify(userRepository).save(entity);

        UserVM expected = UserVM.builder()
                .id(1)
                .build();

        assertEquals(expected, result);
    }
}
