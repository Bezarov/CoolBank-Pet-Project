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
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable UUID accountId) {
        AccountDTO accountDTO = accountService.getAccountById(accountId);
        return ResponseEntity.ok(accountDTO);
    }


    @GetMapping("/username/{accountHolderFullName}")
    public ResponseEntity<List<AccountDTO>> getAllAccountByHolderFullName(@PathVariable String accountHolderFullName) {
        return ResponseEntity.ok(accountService.getAllAccountByHolderFullName(accountHolderFullName));
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<BigDecimal> getBalanceByAccountId(@PathVariable UUID accountId) {
        BigDecimal bigDecimal = accountService.getBalanceByAccountId(accountId);
        return ResponseEntity.ok(bigDecimal);
    }

    @GetMapping("/name/{accountName}")
    public ResponseEntity<AccountDTO> getAccountByAccountName(@PathVariable String accountName) {
        AccountDTO accountDTO = accountService.getAccountByAccountName(accountName);
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDTO>> getAllUserAccountsByUserId(@PathVariable UUID userId) {
        List<AccountDTO> accountDTOS = accountService.getAllUserAccountsByUserId(userId);
        return ResponseEntity.ok(accountDTOS);
    }

    @GetMapping("/user/{userId}/status/{accountStatus}")
    public ResponseEntity<List<AccountDTO>> getAccountsByStatus(@PathVariable UUID userId,
                                                                @PathVariable String accountStatus) {
        List<AccountDTO> accountDTOS = accountService.getAllAccountsByStatus(userId, accountStatus);
        return ResponseEntity.ok(accountDTOS);
    }

    @PatchMapping("/refill/{accountId}/{amount}")
    public ResponseEntity<AccountDTO> refillAccount(@PathVariable UUID accountId, @PathVariable BigDecimal amount) {
        AccountDTO accountDTO = accountService.refillAccount(accountId, amount);
        return ResponseEntity.ok(accountDTO);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<AccountDTO> updateAccountById(@PathVariable UUID accountId,
                                                        @RequestBody AccountDTO accountDTO) {
        AccountDTO ResponseAccountDTO = accountService.updateAccountById(accountId, accountDTO);
        return ResponseEntity.ok(ResponseAccountDTO);
    }

    @PatchMapping("/update/{accountId}/status/{status}")
    public ResponseEntity<AccountDTO> updateAccountStatusById(@PathVariable UUID accountId,
                                                              @PathVariable String status) {
        AccountDTO accountDTO = accountService.updateAccountStatusById(accountId, status);
        return ResponseEntity.ok(accountDTO);
    }

    @PatchMapping("/update/{accountId}/balance/{newBalance}")
    public ResponseEntity<AccountDTO> updateAccountBalanceById(@PathVariable UUID accountId,
                                                               @PathVariable BigDecimal newBalance) {
        AccountDTO accountDTO = accountService.updateAccountBalanceById(accountId, newBalance);
        return ResponseEntity.ok(accountDTO);
    }

    @PatchMapping("/update/{accountName}/{balance}")
    public ResponseEntity<AccountDTO> updateAccountBalanceByAccountName(@PathVariable String accountName,
                                                                        @PathVariable BigDecimal balance) {
        AccountDTO accountDTO = accountService.updateAccountBalanceByAccountName(accountName, balance);
        return ResponseEntity.ok(accountDTO);
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
