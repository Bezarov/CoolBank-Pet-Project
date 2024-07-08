package com.coolbank.service;

import com.coolbank.dto.PaymentDTO;
import com.coolbank.model.Payment;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.CardRepository;
import com.coolbank.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, AccountRepository accountRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }

    private PaymentDTO convertPaymentModelToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFromAccount(payment.getFromAccount().getId());
        paymentDTO.setToAccount(payment.getToAccount().getId());
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setStatus(payment.getStatus());
        paymentDTO.setPaymentType(payment.getPaymentType());
        return paymentDTO;
    }

    private Payment convertPaymentDTOToModel(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setFromAccount(accountRepository.findById(paymentDTO.getFromAccount())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "From Account with ID " + paymentDTO.getFromAccount() + " was NOT Found")));
        payment.setToAccount(accountRepository.findById(paymentDTO.getToAccount())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "To Account with ID " + paymentDTO.getToAccount() + " was NOT Found")));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(paymentDTO.getAmount());
        payment.setDescription(paymentDTO.getDescription());
        payment.setPaymentType(paymentDTO.getPaymentType());
        return payment;
    }

    @Override
    public ResponseEntity<String> createPayment(PaymentDTO paymentDTO) {
        paymentRepository.save(convertPaymentDTOToModel(paymentDTO));
        return new ResponseEntity<>("Payment Successful", HttpStatus.CREATED);
    }

    @Override
    public PaymentDTO getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .map(this::convertPaymentModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment with such ID was NOT Found" + paymentId));
    }

    @Override
    public List<PaymentDTO> getPaymentsByStatus(UUID fromAccountId, String status) {
        accountRepository.findById(fromAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + fromAccountId));
        List<Payment> payments = paymentRepository.findAllByFromAccountId(fromAccountId);
        return payments.stream()
                .filter(payment -> payment.getStatus().equals(status))
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByFromAccount(UUID fromAccountId) {
        accountRepository.findById(fromAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + fromAccountId));
        List<Payment> payments = paymentRepository.findAllByFromAccountId(fromAccountId);
        return payments.stream()
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByToAccount(UUID toAccountId) {
        accountRepository.findById(toAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + toAccountId));
        List<Payment> payments = paymentRepository.findAllByToAccountId(toAccountId);
        return payments.stream()
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByPaymentType(UUID fromAccountId, String paymentType) {
        accountRepository.findById(fromAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + fromAccountId));
        List<Payment> payments = paymentRepository.findAllByPaymentType(paymentType);
        return payments.stream()
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllFromAccountPaymentsByPaymentDateRange(UUID fromAccountId,
                                                                        LocalDateTime fromLocalDateTime,
                                                                        LocalDateTime toLocalDateTime) {
        accountRepository.findById(fromAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + fromAccountId));
        List<Payment> payments = paymentRepository.findAllByFromAccountIdAndPaymentDateBetween(
                fromAccountId, fromLocalDateTime, toLocalDateTime);
        return payments.stream()
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllFromToPaymentsByPaymentDateRange(UUID toAccountId,
                                                                   LocalDateTime fromLocalDateTime,
                                                                   LocalDateTime toLocalDateTime) {
        accountRepository.findById(toAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + toAccountId));
        List<Payment> payments = paymentRepository.findAllByFromAccountIdAndPaymentDateBetween(
                toAccountId, fromLocalDateTime, toLocalDateTime);
        return payments.stream()
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }
}
