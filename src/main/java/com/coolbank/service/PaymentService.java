package com.coolbank.service;

import com.coolbank.dto.PaymentDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PaymentService {
    ResponseEntity<String> createPayment(PaymentDTO paymentDTO);

    PaymentDTO getPaymentById(UUID paymentId);

    List<PaymentDTO> getPaymentsByStatus(UUID fromAccountId, String status);

    List<PaymentDTO> getAllAccountPaymentsByFromAccount(UUID fromAccountId);

    List<PaymentDTO> getAllAccountPaymentsByToAccount(UUID toAccountId);

    List<PaymentDTO> getAllAccountPaymentsByPaymentType(UUID fromAccountId,
                                                        String paymentType);

    List<PaymentDTO> getAllFromAccountPaymentsByPaymentDateRange(UUID fromAccountId,
                                                                 LocalDateTime fromLocalDateTime,
                                                                 LocalDateTime toLocalDateTime);
    List<PaymentDTO> getAllFromToPaymentsByPaymentDateRange(UUID toAccountId,
                                                            LocalDateTime fromLocalDateTime,
                                                            LocalDateTime toLocalDateTime);
}
