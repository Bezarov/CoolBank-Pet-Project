package com.coolbank.service;

import com.coolbank.dto.AccountDTO;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountDTO createAccount(UUID userId, AccountDTO accountDTO);

    AccountDTO getAccountByAccountName(String accountName);

    AccountDTO getAccountById(UUID accountId);

    AccountDTO getAccountByUserId(UUID userId);

    List<AccountDTO> getAllUserAccountsByUserId(UUID userId);

    AccountDTO getAccountByHolderFullName(String accountHolderFullName);

    AccountDTO getBalanceByAccountId(UUID accountId);

    AccountDTO getAccountByStatus(String accountStatus);

    AccountDTO updateAccountById(UUID accountId, AccountDTO accountDTO);

    AccountDTO updateAccountStatusById(UUID accountId, String status);

    AccountDTO updateAccountBalanceById(UUID accountId, Double balance);

    AccountDTO updateAccountBalanceByAccountNumber(String accountNumber, Double balance);

    AccountDTO updateAccountBalanceByAccountName(String accountName, Double balance);

    void deleteAccountByAccountId(UUID accountId);

    void deleteAccountByAccountNumber(String accountNumber);

    void deleteAllUserAccountsByUserId(UUID userId);

}
