package com.clearsolution.user_service.mapper;

import com.clearsolution.user_service.ViewModels.UserVM;
import com.clearsolution.user_service.entity.User;
import com.clearsolution.user_service.request.UserCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {
    UserMapperImpl userMapper = new UserMapperImpl();

    private final String firstName = "Yevhenii";
    private final String lastName = "Ivashchenko";
    private final String email = "yevhenii.28@example.com";
    private final LocalDate birthDate = LocalDate.of(2000, 1, 1);
    private final String address = "123 Main St";
    private final String phoneNumber = "123-456-7890";

    private User user;

    @BeforeEach
    void init() {
        user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .birthDate(birthDate)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }

    @Test
    public void toVMTest() {
        user.setId(1);

        UserVM result = userMapper.toVM(user);

        UserVM expected = UserVM.builder()
                .id(1L)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .birthDate(birthDate)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();

        assertEquals(expected, result);
    }

    @Test
    public void toEntityTest() {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .birthDate(birthDate)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();

        User result = userMapper.toEntity(userCreateRequest);

        assertEquals(user, result);
    }
}
