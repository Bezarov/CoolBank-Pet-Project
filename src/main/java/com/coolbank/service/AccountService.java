package com.coolbank.service;

import com.coolbank.dto.AccountDTO;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    ResponseEntity<String> createAccount(UUID userId, AccountDTO accountDTO);

    AccountDTO getAccountByAccountName(String accountName);

    AccountDTO getAccountById(UUID accountId);

    List<AccountDTO> getAllUserAccountsByUserId(UUID userId);

    AccountDTO getAccountByHolderFullName(String accountHolderFullName);

    BigDecimal getBalanceByAccountId(UUID accountId);

    List<AccountDTO> getAllAccountsByStatus(UUID userId, String accountStatus);

    AccountDTO refillAccount(UUID accountId, BigDecimal amount);

    AccountDTO updateAccountById(UUID accountId, AccountDTO accountDTO);

    AccountDTO updateAccountStatusById(UUID accountId, String status);

    AccountDTO updateAccountBalanceById(UUID accountId, BigDecimal newBalance);

    AccountDTO updateAccountBalanceByAccountName(String accountName, BigDecimal balance);

    void deleteAccountByAccountId(UUID accountId);

    void deleteAccountByAccountName(String accountName);

    void deleteAllUserAccountsByUserId(UUID userId);

}
