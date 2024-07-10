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
    public AccountDTO getAccountByHolderFullName(@PathVariable String accountHolderFullName) {
        return accountService.getAccountByHolderFullName(accountHolderFullName);
    }

    @GetMapping("/balance/{accountId}")
    public BigDecimal getBalanceByAccountId(@PathVariable UUID accountId) {
        return accountService.getBalanceByAccountId(accountId);
    }

    @GetMapping("/name/{accountName}")
    public AccountDTO getAccountByAccountName(@PathVariable String accountName) {
        return accountService.getAccountByAccountName(accountName);
    }

    @GetMapping("/userid/{userId}")
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

    @PatchMapping("/update/{accountId}/{status}")
    public AccountDTO updateAccountStatusById(@PathVariable UUID accountId, @PathVariable String status) {
        return accountService.updateAccountStatusById(accountId, status);
    }

    @PatchMapping("/update/{accountId}/{newBalance}")
    public AccountDTO updateAccountBalanceById(@PathVariable UUID accountId, @PathVariable BigDecimal newBalance) {
        return accountService.updateAccountBalanceById(accountId, newBalance);
    }

    @PatchMapping("/update/{accountName}/{balance}")
    public AccountDTO updateAccountBalanceByAccountName(@PathVariable String accountName,
                                                        @PathVariable BigDecimal balance) {
        return accountService.updateAccountBalanceByAccountName(accountName, balance);
    }

    @DeleteMapping("/delete/id/{accountId}")
    public void deleteAccountByAccountId(@PathVariable UUID accountId) {
        accountService.deleteAccountByAccountId(accountId);
    }

    @DeleteMapping("/delete/name/{accountName}")
    public void deleteAccountByAccountName(@PathVariable String accountName) {
        accountService.deleteAccountByAccountName(accountName);
    }

    @DeleteMapping("/delete/user/{userId}")
    public void deleteAllUserAccountsByUserId(@PathVariable UUID userId) {
        accountService.deleteAllUserAccountsByUserId(userId);
    }
}
