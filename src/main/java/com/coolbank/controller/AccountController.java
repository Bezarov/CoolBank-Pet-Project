package com.coolbank.controller;

import com.coolbank.dto.AccountDTO;
import com.coolbank.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<AccountDTO> createAccount(@PathVariable UUID userId,
                                                    @RequestBody AccountDTO accountDTO) {
        logger.debug("Received POST request to create Account for User with ID: {}, Account: {}",
                userId, accountDTO);
        AccountDTO responseAccountDTO = accountService.createAccount(userId, accountDTO);
        return ResponseEntity.ok(responseAccountDTO);
    }

    @GetMapping("/by-account-id/{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable UUID accountId) {
        logger.debug("Received GET request to get Account by ID: {}", accountId);
        AccountDTO accountDTO = accountService.getAccountById(accountId);
        return ResponseEntity.ok(accountDTO);
    }


    @GetMapping("/by-holder-name/{accountHolderFullName}")
    public ResponseEntity<List<AccountDTO>> getAllAccountsByHolderFullName(@PathVariable
                                                                           String accountHolderFullName) {
        logger.debug("Received GET request to get All User Accounts by HOLDER FULL NAME: {}",
                accountHolderFullName);
        List<AccountDTO> accountDTOS = accountService.getAllAccountsByHolderFullName(accountHolderFullName);
        return ResponseEntity.ok(accountDTOS);
    }

    @GetMapping("/by-account-id/{accountId}/balance")
    public ResponseEntity<BigDecimal> getBalanceByAccountId(@PathVariable UUID accountId) {
        logger.debug("Received GET request to get Account balance by ACCOUNT ID: {}", accountId);
        BigDecimal bigDecimal = accountService.getBalanceByAccountId(accountId);
        return ResponseEntity.ok(bigDecimal);
    }

    @GetMapping("/by-account-name/{accountName}")
    public ResponseEntity<AccountDTO> getAccountByAccountName(@PathVariable String accountName) {
        logger.debug("Received GET request to get Account by ACCOUNT NAME: {}", accountName);
        AccountDTO accountDTO = accountService.getAccountByAccountName(accountName);
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<List<AccountDTO>> getAllUserAccountsByUserId(@PathVariable UUID userId) {
        logger.debug("Received GET request to get All User Accounts by USER ID: {}", userId);
        List<AccountDTO> accountDTOS = accountService.getAllUserAccountsByUserId(userId);
        return ResponseEntity.ok(accountDTOS);
    }

    @GetMapping("/by-user-id/{userId}/status")
    public ResponseEntity<List<AccountDTO>> getAccountsByStatus(
            @PathVariable UUID userId, @RequestParam(name = "status") String accountStatus) {
        logger.debug("Received GET request to get All User (USER ID: {}), Accounts by ACCOUNT STATUS: {}",
                userId, accountStatus);
        List<AccountDTO> accountDTOS = accountService.getAllAccountsByStatus(userId, accountStatus);
        return ResponseEntity.ok(accountDTOS);
    }

    @PatchMapping("/by-account-id/{accountId}/refill")
    public ResponseEntity<AccountDTO> refillAccount(@PathVariable UUID accountId,
                                                    @RequestParam(name = "amount") BigDecimal amount) {
        logger.debug("Received PATCH request to refill Account with ID: {}, in AMOUNT OF: {}",
                accountId, amount);
        AccountDTO accountDTO = accountService.refillAccount(accountId, amount);
        return ResponseEntity.ok(accountDTO);
    }

    @PutMapping("/by-account-id/{accountId}")
    public ResponseEntity<AccountDTO> updateAccountById(@PathVariable UUID accountId,
                                                        @RequestBody AccountDTO accountDTO) {
        logger.debug("Received PUT request to update Account with ID: {}, UPDATE TO: {}",
                accountId, accountDTO);
        AccountDTO ResponseAccountDTO = accountService.updateAccountById(accountId, accountDTO);
        return ResponseEntity.ok(ResponseAccountDTO);
    }

    @PatchMapping("/by-account-id/{accountId}/status")
    public ResponseEntity<AccountDTO> updateAccountStatusById(@PathVariable UUID accountId,
                                                              @RequestParam String status) {
        logger.debug("Received PATCH request to update Account Status with ID: {}, TO: {}",
                accountId, status);
        AccountDTO accountDTO = accountService.updateAccountStatusById(accountId, status);
        return ResponseEntity.ok(accountDTO);
    }

    @PatchMapping("/by-account-id/{accountId}/balance")
    public ResponseEntity<AccountDTO> updateAccountBalanceById(@PathVariable UUID accountId,
                                                               @RequestParam BigDecimal balance) {
        logger.debug("Received PATCH request to update Account Balance with ID: {}, WITH New Balance: {}",
                accountId, balance);
        AccountDTO accountDTO = accountService.updateAccountBalanceById(accountId, balance);
        return ResponseEntity.ok(accountDTO);
    }

    @PatchMapping("/by-account-name/{accountName}/balance")
    public ResponseEntity<AccountDTO> updateAccountBalanceByAccountName(@PathVariable String accountName,
                                                                        @RequestParam BigDecimal balance) {
        logger.debug("Received PATCH request to update Account Balance with ID: {}, WITH New Balance: {}",
                accountName, balance);
        AccountDTO accountDTO = accountService.updateAccountBalanceByAccountName(accountName, balance);
        return ResponseEntity.ok(accountDTO);
    }

    @DeleteMapping("/by-account-id/{accountId}")
    public ResponseEntity<String> deleteAccountByAccountId(@PathVariable UUID accountId) {
        logger.debug("Received DELETE request to remove Account with ID: {} ", accountId);
        return accountService.deleteAccountByAccountId(accountId);
    }

    @DeleteMapping("/by-account-name/{accountName}")
    public ResponseEntity<String> deleteAccountByAccountName(@PathVariable String accountName) {
        logger.debug("Received DELETE request to remove Account with Name: {} ", accountName);
        return accountService.deleteAccountByAccountName(accountName);
    }

    @DeleteMapping("/by-user-id/{userId}")
    public ResponseEntity<String> deleteAllUserAccountsByUserId(@PathVariable UUID userId) {
        logger.debug("Received DELETE request to remove All User Accounts with User ID: {} ", userId);
        return accountService.deleteAllUserAccountsByUserId(userId);
    }
}