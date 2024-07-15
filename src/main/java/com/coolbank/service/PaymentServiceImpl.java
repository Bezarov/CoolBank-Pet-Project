package com.coolbank.service;

import com.coolbank.dto.PaymentDTO;
import com.coolbank.model.Account;
import com.coolbank.model.Card;
import com.coolbank.model.Payment;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.CardRepository;
import com.coolbank.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        paymentDTO.setId(payment.getId());
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
        payment.setPaymentDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        payment.setAmount(paymentDTO.getAmount());
        payment.setDescription(paymentDTO.getDescription());
        payment.setPaymentType(paymentDTO.getPaymentType());
        payment.setStatus("COMPLETED");
        return payment;
    }

    @Override
    @Transactional
    public PaymentDTO createPaymentByAccounts(PaymentDTO paymentDTO) {
        // Finding the account from which the funds will be debited
        Account accountFromAccount = accountRepository.findById(paymentDTO.getFromAccount())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "From Account ID " + paymentDTO.getFromAccount() + " was NOT Found"));
        // Checking availability of sufficient funds
        if (accountFromAccount.getBalance().compareTo(paymentDTO.getAmount()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INSUFFICIENT FUNDS");
        }
        // Subtracting the payment amount from the account balance
        accountFromAccount.setBalance(accountFromAccount.getBalance().subtract(paymentDTO.getAmount()));
        accountRepository.save(accountFromAccount);

        //Find the account to which the funds will be credited
        Account accountToAccount = accountRepository.findById(paymentDTO.getToAccount())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "To Account ID " + paymentDTO.getToAccount() + " was NOT Found"));
        // Crediting the payment amount to your account balance
        accountToAccount.setBalance(accountToAccount.getBalance().add(paymentDTO.getAmount()));
        accountRepository.save(accountToAccount);

        paymentDTO.setPaymentType("Account to Account Transfer");
        Payment payment = paymentRepository.save(convertPaymentDTOToModel(paymentDTO));
        return convertPaymentModelToDTO(payment);
    }

    @Override
    @Transactional
    public PaymentDTO createPaymentByCards(String fromCardNumber, String toCardNumber, BigDecimal amount) {
        // Finding the account from which the funds will be debited
        Account accountFromAccount = cardRepository.findByCardNumber(fromCardNumber)
                .map(Card::getAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Linked Account to this Card Number: " + fromCardNumber + " was NOT Found"));
        // Checking availability of sufficient funds
        if (accountFromAccount.getBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INSUFFICIENT FUNDS");
        }
        // Subtracting the payment amount from the account balance
        accountFromAccount.setBalance(accountFromAccount.getBalance().subtract(amount));
        accountRepository.save(accountFromAccount);

        //Find the account to which the funds will be credited
        Account accountToAccount = cardRepository.findByCardNumber(toCardNumber)
                .map(Card::getAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Linked Account to this Card Number: " + toCardNumber + " was NOT Found"));
        // Crediting the payment amount to your account balance
        accountToAccount.setBalance(accountToAccount.getBalance().add(amount));
        accountRepository.save(accountToAccount);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFromAccount(accountFromAccount.getId());
        paymentDTO.setToAccount(accountToAccount.getId());
        paymentDTO.setAmount(amount);
        paymentDTO.setPaymentType("Card to Card Transfer");
        paymentDTO.setDescription("From Card: " + fromCardNumber + " to Card: " + toCardNumber);
        Payment payment = paymentRepository.save(convertPaymentDTOToModel(paymentDTO));
        return convertPaymentModelToDTO(payment);
    }

    @Override
    public PaymentDTO getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .map(this::convertPaymentModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment with such ID was NOT Found " + paymentId));
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
                                                                        LocalDateTime fromPaymentDate,
                                                                        LocalDateTime toPaymentDate) {
        accountRepository.findById(fromAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found " + fromAccountId));
        List<Payment> payments = paymentRepository.findAllByFromAccountIdAndPaymentDateBetween(
                fromAccountId, fromPaymentDate, toPaymentDate);
        return payments.stream()
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllToAccountPaymentsByPaymentDateRange(UUID toAccountId,
                                                                      LocalDateTime fromPaymentDate,
                                                                      LocalDateTime toPaymentDate) {
        accountRepository.findById(toAccountId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account with such ID was NOT Found " + toAccountId));
        List<Payment> payments = paymentRepository.findAllByToAccountIdAndPaymentDateBetween(
                toAccountId, fromPaymentDate, toPaymentDate);
        return payments.stream()
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }
}
