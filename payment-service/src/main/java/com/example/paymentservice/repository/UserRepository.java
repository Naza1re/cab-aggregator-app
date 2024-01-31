package com.example.paymentservice.repository;

import com.example.paymentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByPassengerId(Long passengerId);
    Optional<User> findByPassengerId(Long id);
}
