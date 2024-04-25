package com.clearsolution.user_service.repository;

import com.clearsolution.user_service.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.birthDate BETWEEN :fromDate AND :toDate")
    List<User> findByBirthDateRange(LocalDate fromDate, LocalDate toDate);
}
