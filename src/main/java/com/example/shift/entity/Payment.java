// src/main/java/com/example/shift/entity/Payment.java
package com.example.shift.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id" )
    private Student student;

    @Column(name = "method_pay")
    private String methodPay;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private LocalDate date;
    private String comment;
}