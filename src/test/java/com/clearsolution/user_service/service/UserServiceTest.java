package com.clearsolution.user_service.service;

import com.clearsolution.user_service.ViewModels.UserVM;
import com.clearsolution.user_service.request.UserCreateRequest;
import com.clearsolution.user_service.request.UserUpdateRequest;
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
import java.util.List;
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
        UserCreateRequest request = UserCreateRequest.builder()
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

        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .firstName(updatedName)
                .lastName(updatedSecondName)
                .email(updatedEmail)
                .birthDate(birthDate)
                .build();

        User user = User.builder()
                .firstName("Current first name")
                .lastName("Current last name")
                .email("john.currentDoe@example.com")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserVM result = userService.updateUserInfo(1L, updateRequest, true);

        verify(userRepository).findById(1L);
        verify(userValidator).validateUserBirthAndAge(birthDate);

        assertEquals(updatedName, result.getFirstName());
        assertEquals(updatedSecondName, result.getLastName());
        assertEquals(updatedEmail, result.getEmail());
    }

    @Test
    void updateAllFieldsTest() {
        String updatedName = "Updated name";
        String updatedSecondName = "Updated second name";
        String updatedEmail = "john.updatedDoe@example.com";
        String updatedAddress = "200 Updated St";
        String updatedPhoneNumber = "999-9999-9999";

        LocalDate birthDate = LocalDate.of(2000, 1, 1);

        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .firstName(updatedName)
                .lastName(updatedSecondName)
                .email(updatedEmail)
                .birthDate(birthDate)
                .address(updatedAddress)
                .phoneNumber(updatedPhoneNumber)
                .build();

        User user = User.builder()
                .id(1)
                .firstName("Current name")
                .lastName("Current name")
                .email("john.currentDoe@example.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("1 Main St")
                .phoneNumber("111-1111-1111")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserVM result = userService.updateUserInfo(1L, updateRequest, true);

        verify(userRepository).findById(1L);
        verify(userValidator).validateUserBirthAndAge(birthDate);

        assertEquals(updatedName, result.getFirstName());
        assertEquals(updatedSecondName, result.getLastName());
        assertEquals(updatedEmail, result.getEmail());
        assertEquals(birthDate, result.getBirthDate());
        assertEquals(updatedPhoneNumber, result.getPhoneNumber());
        assertEquals(updatedAddress, result.getAddress());
    }

    @Test
    void deleteUserByIdTest() {
        userService.deleteUserById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void getUsersByDateTest() {
       LocalDate fromDate = LocalDate.of(2000, 1,1);
       LocalDate toDate = LocalDate.of(2005, 1,1);

       User firstUser = User.builder()
               .id(1)
               .firstName("First user name")
               .lastName("First user surname")
               .email("firstUser@example.com")
               .birthDate(LocalDate.of(2001, 1, 1))
               .build();
        User secondUser = User.builder()
                .id(2)
                .firstName("Second user name")
                .lastName("Second user surname")
                .email("secondUser@example.com")
                .birthDate(LocalDate.of(2003, 1, 1))
                .build();
        User thirdUser = User.builder()
                .id(3)
                .firstName("Third user name")
                .lastName("Third user surname")
                .email("thirdUser@example.com")
                .birthDate(LocalDate.of(2004, 1, 1))
                .build();

       List<User> returningUsers = List.of(firstUser, secondUser, thirdUser);

       when(userRepository.findByBirthDateRange(fromDate, toDate)).thenReturn(returningUsers);

       List<UserVM> result = userService.getUsersByDate(fromDate, toDate);


       UserVM firstExpectedUser = userMapper.toVM(firstUser);
       UserVM secondExpectedUser = userMapper.toVM(secondUser);
       UserVM thirdExpectedUser = userMapper.toVM(thirdUser);

       List<UserVM> expected = List.of(firstExpectedUser, secondExpectedUser, thirdExpectedUser);

       verify(userRepository).findByBirthDateRange(fromDate, toDate);
       assertEquals(expected, result);
    }
}
