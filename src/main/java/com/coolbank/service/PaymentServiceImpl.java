package com.coolbank.service;

import com.coolbank.dto.PaymentDTO;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.CardRepository;
import com.coolbank.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, CardRepository cardRepository,
                              AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        return null;
    }

    @Override
    public PaymentDTO getPaymentById(UUID id) {
        return null;
    }

    @Override
    public PaymentDTO getPaymentByStatus(UUID id, String status) {
        return null;
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByFromAccount(UUID accountId) {
        return null;
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByToAccount(UUID accountId) {
        return null;
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByPaymentType(UUID fromAccountId, String paymentType) {
        return null;
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByPaymentDateRange(LocalDateTime fromLocalDateTime, LocalDateTime toLocalDateTime) {
        return null;
    }
}
