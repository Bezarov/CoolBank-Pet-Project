package com.coolbank.service;

import com.coolbank.dto.AccountDTO;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO, UUID userId);

    AccountDTO getAccountByAccountNumber(String number);

    AccountDTO getAccountById(UUID accountId);

    List<AccountDTO> getAllUserAccountsByUserId(UUID userId);

    AccountDTO getAccountByUserId(UUID userId);

    AccountDTO getAccountByHolderName(String holderName);

    AccountDTO getBalanceByAccountId(UUID accountId);

    AccountDTO getAccountByStatus(String accountStatus);

    AccountDTO updateAccountById(UUID accountId, AccountDTO accountDTO);

    AccountDTO updateAccountStatusById(UUID accountId, String status);

    AccountDTO updateAccountBalanceById(UUID id, Double balance);

    AccountDTO updateAccountBalanceByAccountNumber(String number, Double balance);

    void deleteAccountByAccountId(UUID accountId);

    void deleteAccountByAccountNumber(String accountNumber);

    void deleteAllUserAccountsByUserId(UUID userId);

}
