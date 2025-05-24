// src/main/java/com/example/shift/repository/RoleRepository.java
package com.example.shift.repository;

import com.example.shift.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> { // ID роли может быть Long
    Optional<Role> findByName(String name);
}