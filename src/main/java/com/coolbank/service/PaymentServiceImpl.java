package com.coolbank.service;

import com.coolbank.dto.PaymentDTO;
import com.coolbank.model.Payment;
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

    private PaymentDTO convertPaymentModelToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFromAccount(payment.getFromAccount());
        paymentDTO.setToAccount(payment.getToAccount());
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setStatus(payment.getStatus());
        paymentDTO.setPaymentType(payment.getPaymentType());
        return paymentDTO;
    }

    private Payment convertPaymentDTOToModel(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setFromAccount(paymentDTO.getFromAccount());
        payment.setToAccount(paymentDTO.getToAccount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(paymentDTO.getAmount());
        payment.setDescription(paymentDTO.getDescription());
        payment.setPaymentType(paymentDTO.getPaymentType());
        return payment;
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        return null;
    }

    @Override
    public PaymentDTO getPaymentById(UUID paymentId) {
        return null;
    }

    @Override
    public List<PaymentDTO> getPaymentsByStatus(UUID fromAccountId, String status) {
        return null;
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByFromAccount(UUID fromAccountId) {
        return null;
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByToAccount(UUID toAccountId) {
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
