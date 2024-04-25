package com.clearsolution.user_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "User Service",
                version = "1.0.0",
                description = "API for user management, including creating, updating, and deleting users, as well as searching for users based on various criteria. Supports validation of input data",
                contact = @Contact(
                        name = "Yevhenii Ivashchenko",
                        url = "zhenya.ivashchenko.9494@gmail.com"
                )
        )
)
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
