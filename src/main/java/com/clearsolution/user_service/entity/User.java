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

    public void updateInfo(
            String userFirstName, String userLastName, LocalDate userBirthDate,
            String userPhone, String userEmail, String userAddress
    )
    {
        this.firstName = userFirstName != null ? userFirstName : this.firstName;
        this.lastName = userLastName != null ? userLastName : this.lastName;
        this.birthDate = userBirthDate != null ? userBirthDate : this.birthDate;
        this.phoneNumber = userPhone;
        this.email = userEmail != null ? userEmail : this.email;
        this.address = userAddress;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePersonalInfo(String userFirstName, String userLastName, LocalDate userBirthDate)
    {
        if (firstName != null) {
            this.firstName = userFirstName;
        }

        if (lastName != null) {
            this.lastName = userLastName;
        }

        if (userBirthDate != null) {
            this.birthDate = userBirthDate;
        }

        this.updatedAt = LocalDateTime.now();
    }

    public void updateContactInfo(String userPhone, String userEmail, String userAddress)
    {
        if (userPhone != null) {
            this.phoneNumber = userPhone;
        }

        if (userEmail != null) {
            this.email = userEmail;
        }

        if (userAddress != null) {
            this.address = userAddress;
        }

        this.updatedAt = LocalDateTime.now();
    }
}
