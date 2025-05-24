package com.example.shift.repository;

import com.example.shift.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
}