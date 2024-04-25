package com.clearsolution.user_service.service;

import com.clearsolution.user_service.ViewModels.UserVM;
import com.clearsolution.user_service.dto.UserFieldsUpdateRequest;
import com.clearsolution.user_service.dto.UserRegistrationRequest;
import com.clearsolution.user_service.entity.User;
import com.clearsolution.user_service.mapper.UserMapperImpl;
import com.clearsolution.user_service.repository.UserRepository;
import com.clearsolution.user_service.utils.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapperImpl userMapper;
    @Mock
    private UserValidator userValidator;
    @InjectMocks
    private UserService userService;

    @Test
    void registerUserTest() {
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

        UserVM result = userService.createUser(request);

        UserVM expected = UserVM.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .phoneNumber("123-456-7890")
                .build();

        verify(userRepository).save(entity);

        assertEquals(expected, result);
    }

    @Test
    void updateUserRequiredFieldsTest() {
        String updatedName = "Updated name";
        String updatedSecondName = "Updated second name";
        String updatedEmail = "john.updatedDoe@example.com";

        LocalDate birthDate = LocalDate.of(2000, 1, 1);

        UserFieldsUpdateRequest updateRequest = UserFieldsUpdateRequest.builder()
                .userId(1)
                .firstName(updatedName)
                .lastName(updatedSecondName)
                .email(updatedEmail)
                .birthDate(birthDate)
                .address("200 Main St")
                .phoneNumber("111-1111-1111")
                .build();

        User user = User.builder()
                .firstName("Current first name")
                .lastName("Current last name")
                .email("john.currentDoe@example.com")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserVM result = userService.updateUserPartial(updateRequest);

        verify(userRepository).findById(1L);
        verify(userValidator).validateUserAge(birthDate);
        verify(userValidator).validateUserBirthDate(birthDate);

        assertEquals(updatedName, result.getFirstName());
        assertEquals(updatedSecondName, result.getLastName());
        assertEquals(updatedEmail, result.getEmail());
    }
}
