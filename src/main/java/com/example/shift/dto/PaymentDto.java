// src/main/java/com/example/shift/dto/PaymentDto.java
package com.example.shift.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PaymentDto {
    private Long id;
    private Long studentId;       // ID студента, которому пополняется баланс
    private String studentFullName; // Полное имя студента (для отображения)
    private String methodPay;     // Тип платежа (Visa, Cash, Bank Transfer и т.д.)
    private Integer amount;       // Сумма платежа
    private LocalDate date;       // Дата платежа
    private String comment;       // Комментарий к платежу
}