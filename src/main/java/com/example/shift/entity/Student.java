// src/main/java/com/example/shift/entity/Student.java
package com.example.shift.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phone;

    @Column(name = "data_of_birthday")
    private LocalDate dataOfBirthday;

    @Column(name = "is_active")
    private Boolean isActive = true;
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    private String gender;
    private String comment;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @ManyToMany(mappedBy = "students")
    private Set<Group> groups;
}