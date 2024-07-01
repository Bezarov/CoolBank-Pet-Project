package com.coolbank.service;

import com.coolbank.dto.PaymentDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);

    PaymentDTO getPaymentById(UUID id);

    PaymentDTO getPaymentByStatus(UUID id, String status);

    List<PaymentDTO> getAllAccountPaymentsByFromAccount(UUID accountId);

    List<PaymentDTO> getAllAccountPaymentsByToAccount(UUID accountId);

    List<PaymentDTO> getAllAccountPaymentsByPaymentType(UUID fromAccountId,
                                                        String paymentType);

    List<PaymentDTO> getAllAccountPaymentsByPaymentDateRange(
            LocalDateTime fromLocalDateTime, LocalDateTime toLocalDateTime);
}
