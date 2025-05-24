// src/main/java/com/example/shift/repository/StudentRepository.java
package com.example.shift.repository;

import com.example.shift.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    long countByIsActiveTrue();
    List<Student> findByIsActiveTrueAndIsDeletedFalse();
    List<Student> findByGroups_Id(Long groupId);
}