package com.coolbank.controller;

import com.coolbank.dto.AccountDTO;
import com.coolbank.service.AccountServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register/{userId}")
    public ResponseEntity<String> createAccount(@PathVariable UUID userId, @RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(userId, accountDTO);
    }

    @GetMapping("/id/{accountId}")
    public AccountDTO getAccountById(@PathVariable UUID accountId) {
        return accountService.getAccountById(accountId);
    }


    @GetMapping("/username/{accountHolderFullName}")
    public ResponseEntity<List<AccountDTO>> getAllAccountByHolderFullName(@PathVariable String accountHolderFullName) {
        return ResponseEntity.ok(accountService.getAllAccountByHolderFullName(accountHolderFullName));
    }

    @GetMapping("/balance/{accountId}")
    public BigDecimal getBalanceByAccountId(@PathVariable UUID accountId) {
        return accountService.getBalanceByAccountId(accountId);
    }

    @GetMapping("/name/{accountName}")
    public AccountDTO getAccountByAccountName(@PathVariable String accountName) {
        return accountService.getAccountByAccountName(accountName);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDTO>> getAllUserAccountsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(accountService.getAllUserAccountsByUserId(userId));
    }

    @GetMapping("/user/{userId}/status/{accountStatus}")
    public ResponseEntity<List<AccountDTO>> getAccountsByStatus(@PathVariable UUID userId,
                                                                @PathVariable String accountStatus) {
        return ResponseEntity.ok(accountService.getAllAccountsByStatus(userId, accountStatus));
    }

    @PatchMapping("/refill/{accountId}/{amount}")
    public AccountDTO refillAccount(@PathVariable UUID accountId, @PathVariable BigDecimal amount) {
        return accountService.refillAccount(accountId, amount);
    }

    @PutMapping("/update/{accountId}")
    public AccountDTO updateAccountById(@PathVariable UUID accountId, @RequestBody AccountDTO accountDTO) {
        return accountService.updateAccountById(accountId, accountDTO);
    }

    @PatchMapping("/update/{accountId}/status/{status}")
    public AccountDTO updateAccountStatusById(@PathVariable UUID accountId, @PathVariable String status) {
        return accountService.updateAccountStatusById(accountId, status);
    }

    @PatchMapping("/update/{accountId}/balance/{newBalance}")
    public AccountDTO updateAccountBalanceById(@PathVariable UUID accountId, @PathVariable BigDecimal newBalance) {
        return accountService.updateAccountBalanceById(accountId, newBalance);
    }

    @PatchMapping("/update/{accountName}/{balance}")
    public AccountDTO updateAccountBalanceByAccountName(@PathVariable String accountName,
                                                        @PathVariable BigDecimal balance) {
        return accountService.updateAccountBalanceByAccountName(accountName, balance);
    }

    @DeleteMapping("/delete/id/{accountId}")
    public ResponseEntity<String> deleteAccountByAccountId(@PathVariable UUID accountId) {
        return accountService.deleteAccountByAccountId(accountId);
    }

    @DeleteMapping("/delete/name/{accountName}")
    public ResponseEntity<String> deleteAccountByAccountName(@PathVariable String accountName) {
        return accountService.deleteAccountByAccountName(accountName);
    }

    @DeleteMapping("/delete/user/{userId}")
    public ResponseEntity<String> deleteAllUserAccountsByUserId(@PathVariable UUID userId) {
        return accountService.deleteAllUserAccountsByUserId(userId);
    }
}
