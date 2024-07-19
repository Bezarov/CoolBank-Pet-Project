package com.coolbank.service;

import com.coolbank.dto.PaymentDTO;
import com.coolbank.model.Account;
import com.coolbank.model.Payment;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.CardRepository;
import com.coolbank.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
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
        logger.info("Attempting to find From Account ID: {}", paymentDTO.getFromAccount());
        Account accountFromAccount = accountRepository.findById(paymentDTO.getFromAccount())
                .orElseThrow(() -> {
                    logger.error("From Account ID: {}, was NOT Found", paymentDTO.getFromAccount());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "From Account ID: " + paymentDTO.getFromAccount() + " was NOT Found");
                });
        logger.info("Checking availability of sufficient funds" +
                " From Account with ID: {}", paymentDTO.getFromAccount());
        if (accountFromAccount.getBalance().compareTo(paymentDTO.getAmount()) < 0) {
            logger.error("Insufficient FUNDS for Account with ID: {}", paymentDTO.getFromAccount());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INSUFFICIENT FUNDS");
        }
        accountFromAccount.setBalance(accountFromAccount.getBalance().subtract(paymentDTO.getAmount()));
        accountRepository.save(accountFromAccount);
        logger.info("Funds was Debited from the Account with ID: {}", accountFromAccount.getId());

        logger.info("Attempting to find To Account ID: {}", paymentDTO.getToAccount());
        Account accountToAccount = accountRepository.findById(paymentDTO.getToAccount())
                .orElseThrow(() -> {
                    logger.error("To Account ID: {}, was NOT Found", paymentDTO.getFromAccount());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "To Account ID: " + paymentDTO.getToAccount() + " was NOT Found");
                });
        accountToAccount.setBalance(accountToAccount.getBalance().add(paymentDTO.getAmount()));
        accountRepository.save(accountToAccount);
        logger.info("Funds was Credited from the Account with ID: {}", accountToAccount.getId());


        paymentDTO.setPaymentType("Account to Account Transfer");
        Payment payment = paymentRepository.save(convertPaymentDTOToModel(paymentDTO));
        logger.info("Account to Account transaction ended successfully: {}", payment);
        return convertPaymentModelToDTO(payment);
    }

    @Override
    @Transactional
    public PaymentDTO createPaymentByCards(String fromCardNumber, String toCardNumber, BigDecimal amount) {
        logger.info("Attempting to find Account by Card Number: {}," +
                " from which funds will be Debited", fromCardNumber);
        Account accountFromAccount = cardRepository.findByCardNumber(fromCardNumber)
                .map(CardEntity -> {
                    logger.info("Account was found with ID: {}", CardEntity.getAccount().getId());
                    return CardEntity.getAccount();
                })
                .orElseThrow(() -> {
                    logger.error("Linked Account to this Card Number was not found: {}", fromCardNumber);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Linked Account to this Card Number: " + fromCardNumber + " was NOT Found");
                });
        logger.info("Checking availability of sufficient funds" +
                " From Account with ID: {}", accountFromAccount.getId());
        if (accountFromAccount.getBalance().compareTo(amount) < 0) {
            logger.error("Insufficient FUNDS for Account with ID: {}", accountFromAccount.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INSUFFICIENT FUNDS");
        }

        accountFromAccount.setBalance(accountFromAccount.getBalance().subtract(amount));
        accountRepository.save(accountFromAccount);
        logger.info("Funds was debited from the Account with ID: {}", accountFromAccount.getId());

        logger.info("Attempting to find Account by Card Number: {}," +
                " from which funds will be Credited", toCardNumber);
        Account accountToAccount = cardRepository.findByCardNumber(toCardNumber)
                .map(CardEntity -> {
                    logger.info("Account was found with ID: {}", CardEntity.getAccount().getId());
                    return CardEntity.getAccount();
                })
                .orElseThrow(() -> {
                    logger.error("Linked Account to this Card Number was not found: {}", toCardNumber);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Linked Account to this Card Number: " + toCardNumber + " was NOT Found");
                });

        accountToAccount.setBalance(accountToAccount.getBalance().add(amount));
        accountRepository.save(accountToAccount);
        logger.info("Funds was Credited from the Account with ID: {}", accountFromAccount.getId());

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setFromAccount(accountFromAccount.getId());
        paymentDTO.setToAccount(accountToAccount.getId());
        paymentDTO.setAmount(amount);
        paymentDTO.setPaymentType("Card to Card Transfer");
        paymentDTO.setDescription("From Card: " + fromCardNumber + " to Card: " + toCardNumber);
        Payment payment = paymentRepository.save(convertPaymentDTOToModel(paymentDTO));
        logger.info("Card to Card transaction ended successfully: {}", payment);
        return convertPaymentModelToDTO(payment);
    }

    @Override
    public PaymentDTO getPaymentById(UUID paymentId) {
        logger.info("Attempting to find Payment with ID: {}", paymentId);
        return paymentRepository.findById(paymentId)
                .map(PaymentEntity -> {
                    logger.info("Payment was found and received to the Controller: {}", PaymentEntity);
                    return convertPaymentModelToDTO(PaymentEntity);
                })
                .orElseThrow(() -> {
                    logger.error("Payment with such ID: {} was not found", paymentId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Payment with such ID was NOT Found: " + paymentId);
                });
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByFromAccount(UUID fromAccountId) {
        logger.info("Attempting to find Account with ID: {}", fromAccountId);
        accountRepository.findById(fromAccountId).orElseThrow(() -> {
            logger.error("Account with such ID: {} was not found", fromAccountId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account with such ID was NOT Found: " + fromAccountId);
        });

        logger.info("Attempting to find All Account Payments with Account ID: {}", fromAccountId);
        List<Payment> payments = paymentRepository.findAllByFromAccountId(fromAccountId);
        logger.info("Payments was found and received to the Controller: {}", payments);
        return payments.stream()
                .map(this::convertPaymentModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getPaymentsByStatus(UUID fromAccountId, String status) {
        logger.info("Attempting to find Account with ID: {}", fromAccountId);
        accountRepository.findById(fromAccountId).orElseThrow(() -> {
            logger.error("Account with such ID: {} was not found", fromAccountId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account with such ID was NOT Found: " + fromAccountId);
        });

        logger.info("Attempting to find All Account Payments with Status: {}", status);
        List<Payment> payments = paymentRepository.findAllByFromAccountId(fromAccountId);
        return payments.stream()
                .filter(payment -> payment.getStatus().equals(status))
                .map(FilteredEntity -> {
                    logger.info("Payments was found and received to the Controller: {}", FilteredEntity);
                    return convertPaymentModelToDTO(FilteredEntity);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByToAccount(UUID toAccountId) {
        logger.info("Attempting to find Account with ID: {}", toAccountId);
        accountRepository.findById(toAccountId).orElseThrow(() -> {
            logger.error("Account with such ID: {} was not found", toAccountId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account with such ID was NOT Found: " + toAccountId);
        });

        logger.info("Attempting to find All Payments To Account with ID: {}", toAccountId);
        List<Payment> payments = paymentRepository.findAllByToAccountId(toAccountId);
        return payments.stream()
                .map(PaymentEntity -> {
                    logger.info("Payments was found and received to the Controller: {}", PaymentEntity);
                    return convertPaymentModelToDTO(PaymentEntity);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllAccountPaymentsByPaymentType(UUID fromAccountId, String paymentType) {
        logger.info("Attempting to find Account with ID: {}", fromAccountId);
        accountRepository.findById(fromAccountId).orElseThrow(() -> {
            logger.error("Account with such ID: {} was not found", fromAccountId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account with such ID was NOT Found: " + fromAccountId);
        });

        logger.info("Attempting to find All Account Payments with Type: {}", paymentType);
        List<Payment> payments = paymentRepository.findAllByPaymentType(paymentType);
        return payments.stream()
                .map(PaymentEntity -> {
                    logger.info("Payments was found and received to the Controller: {}", PaymentEntity);
                    return convertPaymentModelToDTO(PaymentEntity);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllFromAccountPaymentsByPaymentDateRange(UUID fromAccountId,
                                                                        LocalDateTime fromPaymentDate,
                                                                        LocalDateTime toPaymentDate) {
        logger.info("Attempting to find Account with ID: {}", fromAccountId);
        accountRepository.findById(fromAccountId).orElseThrow(() -> {
            logger.error("Account with such ID: {} was not found", fromAccountId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account with such ID was NOT Found: " + fromAccountId);
        });

        logger.info("Attempting to find All Account Payments with Date Range: " +
                "{}-{}", fromPaymentDate, toPaymentDate);
        List<Payment> payments = paymentRepository.findAllByFromAccountIdAndPaymentDateBetween(
                fromAccountId, fromPaymentDate, toPaymentDate);
        return payments.stream()
                .map(PaymentEntity -> {
                    logger.info("Payments was found and received to the Controller: {}", PaymentEntity);
                    return convertPaymentModelToDTO(PaymentEntity);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllToAccountPaymentsByPaymentDateRange(UUID toAccountId,
                                                                      LocalDateTime fromPaymentDate,
                                                                      LocalDateTime toPaymentDate) {
        logger.info("Attempting to find Account with ID: {}", toAccountId);
        accountRepository.findById(toAccountId).orElseThrow(() -> {
            logger.error("Account with such ID: {} was not found", toAccountId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account with such ID was NOT Found: " + toAccountId);
        });

        logger.info("Attempting to find All To Account Payments with Date Range: " +
                "{}-{}", fromPaymentDate, toPaymentDate);
        List<Payment> payments = paymentRepository.findAllByToAccountIdAndPaymentDateBetween(
                toAccountId, fromPaymentDate, toPaymentDate);
        return payments.stream()
                .map(PaymentEntity -> {
                    logger.info("Payments was found and received to the Controller: {}", PaymentEntity);
                    return convertPaymentModelToDTO(PaymentEntity);
                })
                .collect(Collectors.toList());
    }
}
