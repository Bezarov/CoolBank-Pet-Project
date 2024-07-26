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
@RequestMapping("/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/account/transfer")
    public ResponseEntity<PaymentDTO> createPaymentByAccounts(@RequestBody PaymentDTO paymentDTO) {
        logger.debug("Received POST request for Transfer from Account to Account: {}", paymentDTO);
        PaymentDTO responsePaymentDTO = paymentService.createPaymentByAccounts(paymentDTO);
        return ResponseEntity.ok(responsePaymentDTO);
    }

    @PostMapping("/card/transfer")
    public ResponseEntity<PaymentDTO> createPaymentByCards(
            @RequestParam(name = "from-card-number") String fromCardNumber,
            @RequestParam(name = "to-card-number") String toCardNumber,
            @RequestParam(name = "amount") BigDecimal amount) {
        logger.debug("Received POST request to Card to Card Transfer: From Card: {}," +
                " To Card: {}, in AMOUNT OF: {}", fromCardNumber, toCardNumber, amount);
        PaymentDTO paymentDTO = paymentService.createPaymentByCards(fromCardNumber, toCardNumber, amount);
        return ResponseEntity.ok(paymentDTO);
    }

    @GetMapping("/by-payment-id/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable UUID paymentId) {
        logger.debug("Received GET request to get Payment by ID: {}", paymentId);
        PaymentDTO paymentDTO = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(paymentDTO);
    }

    @GetMapping("/from-account-id/{fromAccountId}")
    public ResponseEntity<List<PaymentDTO>> getAllAccountPaymentsByFromAccount(@PathVariable UUID fromAccountId) {
        logger.debug("Received GET request to get All Payments from Account ID: {}", fromAccountId);
        List<PaymentDTO> paymentDTOS = paymentService.getAllAccountPaymentsByFromAccount(fromAccountId);
        return ResponseEntity.ok(paymentDTOS);
    }

    @GetMapping("/to-account-id/{toAccountId}")
    public ResponseEntity<List<PaymentDTO>> getAllAccountPaymentsByToAccount(@PathVariable UUID toAccountId) {
        logger.debug("Received GET request to get All Payments to Account ID: {}", toAccountId);
        List<PaymentDTO> paymentDTOS = paymentService.getAllAccountPaymentsByToAccount(toAccountId);
        return ResponseEntity.ok(paymentDTOS);
    }

    @GetMapping("/from-account-id/{fromAccountId}/status")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByStatus(
            @PathVariable UUID fromAccountId,
            @RequestParam String status) {
        logger.debug("Received GET request to get All Payments from Account ID: {}," +
                " with Status: {}", fromAccountId, status);
        List<PaymentDTO> paymentDTOS = paymentService.getPaymentsByStatus(fromAccountId, status);
        return ResponseEntity.ok(paymentDTOS);
    }

    @GetMapping("/from-account-id/{fromAccountId}/type")
    public ResponseEntity<List<PaymentDTO>> getAllAccountPaymentsByPaymentType(
            @PathVariable UUID fromAccountId,
            @RequestParam(name = "payment-type") String paymentType) {
        logger.debug("Received GET request to get All Payments from Account ID: {}," +
                " with Type: {}", fromAccountId, paymentType);
        List<PaymentDTO> paymentDTOS = paymentService.getAllAccountPaymentsByPaymentType(
                fromAccountId, paymentType);
        return ResponseEntity.ok(paymentDTOS);
    }

    @GetMapping("/from-account-id/{fromAccountId}/date-range")
    public ResponseEntity<List<PaymentDTO>> getAllFromAccountPaymentsByPaymentDateRange(
            @PathVariable UUID fromAccountId,
            @RequestParam(name = "from-date") LocalDateTime fromPaymentDate,
            @RequestParam(name = "to-date") LocalDateTime toPaymentDate) {
        logger.debug("Received GET request to get All Account Payments in date range FROM" +
                        " Account with ID: {}, from: {}, to: {} ",
                fromAccountId, fromPaymentDate, toPaymentDate);
        List<PaymentDTO> paymentDTOS = paymentService.getAllFromAccountPaymentsByPaymentDateRange(
                fromAccountId, fromPaymentDate, toPaymentDate);
        return ResponseEntity.ok(paymentDTOS);
    }

    @GetMapping("/to-account-id/{toAccountId}/date-range")
    public ResponseEntity<List<PaymentDTO>> getAllFromToPaymentsByPaymentDateRange(
            @PathVariable UUID toAccountId,
            @RequestParam(name = "from-date") LocalDateTime fromPaymentDate,
            @RequestParam(name = "to-date") LocalDateTime toPaymentDate) {
        logger.debug("Received GET request to get All Account Payments in date range TO" +
                        " Account with ID: {}, from: {}, to: {} ",
                toPaymentDate, fromPaymentDate, toPaymentDate);
        List<PaymentDTO> paymentDTOS = paymentService.getAllToAccountPaymentsByPaymentDateRange(
                toAccountId, fromPaymentDate, toPaymentDate);
        return ResponseEntity.ok(paymentDTOS);
    }
}