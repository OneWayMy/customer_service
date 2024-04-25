package com.clearsolution.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", length = 64, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 64, nullable = false)
    private String lastName;

    @Column(name = "email", length = 64, nullable = false, unique = true)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "address", length = 128)
    private String address;

    @Column(name = "phone_number", length = 32, unique = true)
    private String phoneNumber;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updatePersonalInfo(String customerFirstName, String customerLastName, LocalDate customerBirthDate)
    {
        if (firstName != null) {
            this.firstName = customerFirstName;
        }

        if (lastName != null) {
            this.lastName = customerLastName;
        }

        if (customerBirthDate != null) {
            this.birthDate = customerBirthDate;
        }
    }

    public void updateContactInfo(String customerPhone, String customerEmail, String customerAddress)
    {
        if (customerPhone != null) {
            this.phoneNumber = customerPhone;
        }

        if (customerEmail != null) {
            this.email = customerEmail;
        }

        if (customerAddress != null) {
            this.address = customerAddress;
        }
    }
}
