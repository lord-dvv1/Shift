package com.example.shift.repository;// src/main/java/com/example/shift/repository/UserRepository.java


import com.example.shift.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByPhone(String phone);

}