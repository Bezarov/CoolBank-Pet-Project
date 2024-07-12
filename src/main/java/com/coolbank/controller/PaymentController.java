package com.coolbank.controller;

import com.coolbank.dto.PaymentDTO;
import com.coolbank.service.PaymentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/register/account")
    public ResponseEntity<PaymentDTO> createPaymentByAccounts(@RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(paymentService.createPaymentByAccounts(paymentDTO));
    }

    @PostMapping("/register/card/from/{fromCardNumber}/to/{toCardNumber}/amount/{amount}")
    public ResponseEntity<PaymentDTO> createPaymentByCards(@PathVariable String fromCardNumber, @PathVariable String toCardNumber,
                                                           @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(paymentService.createPaymentByCards(fromCardNumber, toCardNumber, amount));
    }

    @GetMapping("/id/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable UUID paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/from/{fromAccountId}")
    public ResponseEntity<List<PaymentDTO>> getAllAccountPaymentsByFromAccount(@PathVariable UUID fromAccountId) {
        return ResponseEntity.ok(paymentService.getAllAccountPaymentsByFromAccount(fromAccountId));
    }

    @GetMapping("/to/{toAccountId}")
    public ResponseEntity<List<PaymentDTO>> getAllAccountPaymentsByToAccount(@PathVariable UUID toAccountId) {
        return ResponseEntity.ok(paymentService.getAllAccountPaymentsByToAccount(toAccountId));
    }

    @GetMapping("/from/{fromAccountId}/status/{status}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByStatus(@PathVariable UUID fromAccountId,
                                                                @PathVariable String status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(fromAccountId, status));
    }

    @GetMapping("/from/{fromAccountId}/type/{paymentType}")
    public ResponseEntity<List<PaymentDTO>> getAllAccountPaymentsByPaymentType(
            @PathVariable UUID fromAccountId, @PathVariable String paymentType) {
        return ResponseEntity.ok(paymentService.getAllAccountPaymentsByPaymentType(fromAccountId, paymentType));
    }

    @GetMapping("/from/{fromAccountId}/date/{fromPaymentDate}/date/{toPaymentDate}")
    public ResponseEntity<List<PaymentDTO>> getAllFromAccountPaymentsByPaymentDateRange(
            @PathVariable UUID fromAccountId, @PathVariable LocalDateTime fromPaymentDate,
            @PathVariable LocalDateTime toPaymentDate) {
        return ResponseEntity.ok(paymentService.getAllFromAccountPaymentsByPaymentDateRange(
                fromAccountId, fromPaymentDate, toPaymentDate));
    }

    @GetMapping("/to/{toAccountId}/date/{fromPaymentDate}/date/{toPaymentDate}")
    public ResponseEntity<List<PaymentDTO>> getAllFromToPaymentsByPaymentDateRange(
            @PathVariable UUID toAccountId, @PathVariable LocalDateTime fromPaymentDate,
            @PathVariable LocalDateTime toPaymentDate) {
        return ResponseEntity.ok(paymentService.getAllToAccountPaymentsByPaymentDateRange(
                toAccountId, fromPaymentDate, toPaymentDate));
    }
}
