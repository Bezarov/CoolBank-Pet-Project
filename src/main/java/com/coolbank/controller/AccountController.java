package com.coolbank.controller;

import com.coolbank.dto.AccountDTO;
import com.coolbank.service.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register/user/{userId}")
    public ResponseEntity<AccountDTO> createAccount(@PathVariable UUID userId,
                                                    @RequestBody AccountDTO accountDTO) {
        logger.debug("Received POST request to create Account for User with ID: {}, Account: {}",
                userId, accountDTO);
        return ResponseEntity.ok(accountService.createAccount(userId, accountDTO));
    }

    @GetMapping("/id/{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable UUID accountId) {
        logger.debug("Received GET request to get Account by ID: {}", accountId);
        AccountDTO accountDTO = accountService.getAccountById(accountId);
        return ResponseEntity.ok(accountDTO);
    }


    @GetMapping("/username/{accountHolderFullName}")
    public ResponseEntity<List<AccountDTO>> getAllAccountsByHolderFullName(@PathVariable
                                                                           String accountHolderFullName) {
        logger.debug("Received GET request to get All User Accounts by HOLDER FULL NAME: {}",
                accountHolderFullName);
        return ResponseEntity.ok(accountService.getAllAccountsByHolderFullName(accountHolderFullName));
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<BigDecimal> getBalanceByAccountId(@PathVariable UUID accountId) {
        logger.debug("Received GET request to get Account balance by ACCOUNT ID: {}", accountId);
        BigDecimal bigDecimal = accountService.getBalanceByAccountId(accountId);
        return ResponseEntity.ok(bigDecimal);
    }

    @GetMapping("/name/{accountName}")
    public ResponseEntity<AccountDTO> getAccountByAccountName(@PathVariable String accountName) {
        logger.debug("Received GET request to get Account by ACCOUNT NAME: {}", accountName);
        AccountDTO accountDTO = accountService.getAccountByAccountName(accountName);
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDTO>> getAllUserAccountsByUserId(@PathVariable UUID userId) {
        logger.debug("Received GET request to get All User Accounts by USER ID: {}", userId);
        List<AccountDTO> accountDTOS = accountService.getAllUserAccountsByUserId(userId);
        return ResponseEntity.ok(accountDTOS);
    }

    @GetMapping("/user/{userId}/status/{accountStatus}")
    public ResponseEntity<List<AccountDTO>> getAccountsByStatus(@PathVariable UUID userId,
                                                                @PathVariable String accountStatus) {
        logger.debug("Received GET request to get All User (USER ID: {}), Accounts by ACCOUNT STATUS: {}",
                userId, accountStatus);
        List<AccountDTO> accountDTOS = accountService.getAllAccountsByStatus(userId, accountStatus);
        return ResponseEntity.ok(accountDTOS);
    }

    @PatchMapping("/refill/id/{accountId}/amount/{amount}")
    public ResponseEntity<AccountDTO> refillAccount(@PathVariable UUID accountId,
                                                    @PathVariable BigDecimal amount) {
        logger.debug("Received PATCH request to refill Account with ID: {}, in AMOUNT OF: {}",
                accountId, amount);
        AccountDTO accountDTO = accountService.refillAccount(accountId, amount);
        return ResponseEntity.ok(accountDTO);
    }

    @PutMapping("/update/id/{accountId}")
    public ResponseEntity<AccountDTO> updateAccountById(@PathVariable UUID accountId,
                                                        @RequestBody AccountDTO accountDTO) {
        logger.debug("Received PUT request to update Account with ID: {}, UPDATE TO: {}",
                accountId, accountDTO);
        AccountDTO ResponseAccountDTO = accountService.updateAccountById(accountId, accountDTO);
        return ResponseEntity.ok(ResponseAccountDTO);
    }

    @PatchMapping("/update/id/{accountId}/status/{status}")
    public ResponseEntity<AccountDTO> updateAccountStatusById(@PathVariable UUID accountId,
                                                              @PathVariable String status) {
        logger.debug("Received PATCH request to update Account Status with ID: {}, TO: {}",
                accountId, status);
        AccountDTO accountDTO = accountService.updateAccountStatusById(accountId, status);
        return ResponseEntity.ok(accountDTO);
    }

    @PatchMapping("/update/id/{accountId}/balance/{newBalance}")
    public ResponseEntity<AccountDTO> updateAccountBalanceById(@PathVariable UUID accountId,
                                                               @PathVariable BigDecimal newBalance) {
        logger.debug("Received PATCH request to update Account Balance with ID: {}, WITH New Balance: {}",
                accountId, newBalance);
        AccountDTO accountDTO = accountService.updateAccountBalanceById(accountId, newBalance);
        return ResponseEntity.ok(accountDTO);
    }

    @PatchMapping("/update/name/{accountName}/{balance}")
    public ResponseEntity<AccountDTO> updateAccountBalanceByAccountName(@PathVariable String accountName,
                                                                        @PathVariable BigDecimal balance) {
        logger.debug("Received PATCH request to update Account Balance with ID: {}, WITH New Balance: {}",
                accountName, balance);
        AccountDTO accountDTO = accountService.updateAccountBalanceByAccountName(accountName, balance);
        return ResponseEntity.ok(accountDTO);
    }

    @DeleteMapping("/delete/id/{accountId}")
    public ResponseEntity<String> deleteAccountByAccountId(@PathVariable UUID accountId) {
        logger.debug("Received DELETE request to remove Account with ID: {} ", accountId);
        return accountService.deleteAccountByAccountId(accountId);
    }

    @DeleteMapping("/delete/name/{accountName}")
    public ResponseEntity<String> deleteAccountByAccountName(@PathVariable String accountName) {
        logger.debug("Received DELETE request to remove Account with Name: {} ", accountName);
        return accountService.deleteAccountByAccountName(accountName);
    }

    @DeleteMapping("/delete/user/{userId}")
    public ResponseEntity<String> deleteAllUserAccountsByUserId(@PathVariable UUID userId) {
        logger.debug("Received DELETE request to remove All User Accounts with ID: {} ", userId);
        return accountService.deleteAllUserAccountsByUserId(userId);
    }
}