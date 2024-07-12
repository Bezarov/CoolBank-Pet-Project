package com.coolbank.service;

import com.coolbank.dto.PaymentDTO;
import com.coolbank.model.Account;
import com.coolbank.model.Card;
import com.coolbank.model.Payment;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.CardRepository;
import com.coolbank.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, AccountRepository accountRepository,
                              CardRepository cardRepository) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
    }

    private PaymentDTO convertPaymentModelToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFromAccount(payment.getFromAccount().getId());
        paymentDTO.setToAccount(payment.getToAccount().getId());
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setStatus(payment.getStatus());
        paymentDTO.setPaymentType(payment.getPaymentType());
        paymentDTO.setDescription(payment.getDescription());
        return paymentDTO;
    }

    private Payment convertPaymentDTOToModel(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setFromAccount(accountRepository.findById(paymentDTO.getFromAccount())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "From Account ID " + paymentDTO.getFromAccount() + " was NOT Found")));
        payment.setToAccount(accountRepository.findById(paymentDTO.getToAccount())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "To Account ID " + paymentDTO.getToAccount() + " was NOT Found")));
        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(paymentDTO.getAmount());
        payment.setDescription(paymentDTO.getDescription());
        payment.setPaymentType(paymentDTO.getPaymentType());
        return payment;
    }

    @Override
    public ResponseEntity<String> createPaymentFromAccountIdToAccountId(PaymentDTO paymentDTO) {
        paymentRepository.save(convertPaymentDTOToModel(paymentDTO));
        return new ResponseEntity<>("Payment Successful", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> createPaymentFromCardNumberToCardNumber(String fromCardNumber,
                                                                          String toCardNumber,
                                                                          BigDecimal amount) {
        UUID fromAccountId = cardRepository.findByCardNumber(fromCardNumber)
                .map(Card::getAccount)
                .map(Account::getId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Linked Account to this Card Number: " + fromCardNumber + " was NOT Found"));
        UUID toAccountId = cardRepository.findByCardNumber(toCardNumber)
                .map(Card::getAccount)
                .map(Account::getId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Linked Account to this Card Number: " + toCardNumber + " was NOT Found"));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(amount);
        paymentDTO.setFromAccount(fromAccountId);
        paymentDTO.setToAccount(toAccountId);
        paymentDTO.setPaymentDate(LocalDateTime.now());
        paymentDTO.setStatus("COMPLETED");
        paymentDTO.setPaymentType("TRANSFER");
        paymentDTO.setDescription("Card transfer to: " + toCardNumber);
        paymentRepository.save(convertPaymentDTOToModel(paymentDTO));
        return new ResponseEntity<>("Transfer Successful", HttpStatus.CREATED);
    }

    @Override
    public PaymentDTO getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .map(this::convertPaymentModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment with such ID was NOT Found " + paymentId));
    }

    @Override
    public List<PaymentDTO> getPaymentsByStatus(UUID fromAccountId, String status) {
        accountRepository.findById(fromAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found " + fromAccountId));
        List<Payment> payments = paymentRepository.findAllByFromAccountId(fromAccountId);
        return payments.stream()
                .filter(payment -> payment.getStatus().equals(status))
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByFromAccount(UUID fromAccountId) {
        accountRepository.findById(fromAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found " + fromAccountId));
        List<Payment> payments = paymentRepository.findAllByFromAccountId(fromAccountId);
        return payments.stream()
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByToAccount(UUID toAccountId) {
        accountRepository.findById(toAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found " + toAccountId));
        List<Payment> payments = paymentRepository.findAllByToAccountId(toAccountId);
        return payments.stream()
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByPaymentType(UUID fromAccountId, String paymentType) {
        accountRepository.findById(fromAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found " + fromAccountId));
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
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found " + fromAccountId));
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
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found " + toAccountId));
        List<Payment> payments = paymentRepository.findAllByFromAccountIdAndPaymentDateBetween(
                toAccountId, fromLocalDateTime, toLocalDateTime);
        return payments.stream()
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }
}
