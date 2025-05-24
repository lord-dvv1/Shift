// src/main/java/com/example/shift/service/impl/PaymentServiceImpl.java
package com.example.shift.service.impl;

import com.example.shift.dto.PaymentDto;
import com.example.shift.entity.Payment;
import com.example.shift.entity.Student;
import com.example.shift.repository.PaymentRepository;
import com.example.shift.repository.StudentRepository;
import com.example.shift.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByStudentId(Long studentId) {
        return paymentRepository.findByStudentId(studentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentDto addPayment(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setMethodPay(paymentDto.getMethodPay());
        payment.setAmount(paymentDto.getAmount());
        payment.setDate(paymentDto.getDate());
        payment.setComment(paymentDto.getComment());

        studentRepository.findById(paymentDto.getStudentId())
                .ifPresent(payment::setStudent);

        payment = paymentRepository.save(payment);
        return convertToDto(payment);
    }

    @Override
    public Optional<PaymentDto> getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(this::convertToDto);
    }

    private PaymentDto convertToDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setMethodPay(payment.getMethodPay());
        dto.setAmount(payment.getAmount());
        dto.setDate(payment.getDate());
        dto.setComment(payment.getComment());
        if (payment.getStudent() != null) {
            dto.setStudentId(payment.getStudent().getId());
            dto.setStudentFullName(payment.getStudent().getFullName());
        }
        return dto;
    }
}