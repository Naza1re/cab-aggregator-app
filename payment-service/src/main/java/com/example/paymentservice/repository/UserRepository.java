package com.example.paymentservice.repository;

import com.example.paymentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsById(Long id);
}
