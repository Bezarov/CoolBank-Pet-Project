package com.coolbank.controller;

import com.coolbank.dto.PaymentDTO;
import com.coolbank.service.PaymentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/register/account")
    public ResponseEntity<PaymentDTO> createPaymentByAccounts(@RequestBody PaymentDTO paymentDTO) {
        logger.debug("Received POST request for Transfer from Account to Account: {}", paymentDTO);
        return ResponseEntity.ok(paymentService.createPaymentByAccounts(paymentDTO));
    }

    @PostMapping("/register/card/from/{fromCardNumber}/to/{toCardNumber}/amount/{amount}")
    public ResponseEntity<PaymentDTO> createPaymentByCards(@PathVariable String fromCardNumber,
                                                           @PathVariable String toCardNumber,
                                                           @PathVariable BigDecimal amount) {
        logger.debug("Received POST request for Transfer from Card to Card From Card: {}," +
                " To Card: {}, in AMOUNT OF: {}", fromCardNumber, toCardNumber, amount);
        return ResponseEntity.ok(paymentService.createPaymentByCards(fromCardNumber, toCardNumber, amount));
    }

    @GetMapping("/id/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable UUID paymentId) {
        logger.debug("Received GET request to get Payment by ID: {}", paymentId);
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/from/accountid/{fromAccountId}")
    public ResponseEntity<List<PaymentDTO>> getAllAccountPaymentsByFromAccount(@PathVariable UUID fromAccountId) {
        logger.debug("Received GET request to get All Payments from Account ID: {}", fromAccountId);
        return ResponseEntity.ok(paymentService.getAllAccountPaymentsByFromAccount(fromAccountId));
    }

    @GetMapping("/to/accountid/{toAccountId}")
    public ResponseEntity<List<PaymentDTO>> getAllAccountPaymentsByToAccount(@PathVariable UUID toAccountId) {
        logger.debug("Received GET request to get All Payments to Account ID: {}", toAccountId);
        return ResponseEntity.ok(paymentService.getAllAccountPaymentsByToAccount(toAccountId));
    }

    @GetMapping("/from/accountid/{fromAccountId}/status/{status}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByStatus(@PathVariable UUID fromAccountId,
                                                                @PathVariable String status) {
        logger.debug("Received GET request to get All Payments from Account ID: {}," +
                " with Status: {}", fromAccountId, status);
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(fromAccountId, status));
    }

    @GetMapping("/from/accountid/{fromAccountId}/type/{paymentType}")
    public ResponseEntity<List<PaymentDTO>> getAllAccountPaymentsByPaymentType(
            @PathVariable UUID fromAccountId, @PathVariable String paymentType) {
        logger.debug("Received GET request to get All Payments from Account ID: {}," +
                " with Type: {}", fromAccountId, paymentType);
        return ResponseEntity.ok(paymentService.getAllAccountPaymentsByPaymentType(fromAccountId, paymentType));
    }

    @GetMapping("/from/accountid/{fromAccountId}/date/{fromPaymentDate}/date/{toPaymentDate}")
    public ResponseEntity<List<PaymentDTO>> getAllFromAccountPaymentsByPaymentDateRange(
            @PathVariable UUID fromAccountId, @PathVariable LocalDateTime fromPaymentDate,
            @PathVariable LocalDateTime toPaymentDate) {
        logger.debug("Received GET request to get All Account Payments in date range FROM" +
                        " Account with ID: {}, from: {}, up to: {} ",
                fromAccountId, fromPaymentDate, toPaymentDate);
        return ResponseEntity.ok(paymentService.getAllFromAccountPaymentsByPaymentDateRange(
                fromAccountId, fromPaymentDate, toPaymentDate));
    }

    @GetMapping("/to/accountid/{toAccountId}/date/{fromPaymentDate}/date/{toPaymentDate}")
    public ResponseEntity<List<PaymentDTO>> getAllFromToPaymentsByPaymentDateRange(
            @PathVariable UUID toAccountId, @PathVariable LocalDateTime fromPaymentDate,
            @PathVariable LocalDateTime toPaymentDate) {
        logger.debug("Received GET request to get All Account Payments in date range TO" +
                        " Account with ID: {}, from: {}, up to: {} ",
                toPaymentDate, fromPaymentDate, toPaymentDate);
        return ResponseEntity.ok(paymentService.getAllToAccountPaymentsByPaymentDateRange(
                toAccountId, fromPaymentDate, toPaymentDate));
    }
}