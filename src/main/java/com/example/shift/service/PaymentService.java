// src/main/java/com/example/shift/service/PaymentService.java
package com.example.shift.service;

import com.example.shift.dto.PaymentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public interface PaymentService {
    List<PaymentDto> getAllPayments();
    List<PaymentDto> getPaymentsByStudentId(Long studentId);
    PaymentDto addPayment(PaymentDto paymentDto);
    Optional<PaymentDto> getPaymentById(Long id);
}