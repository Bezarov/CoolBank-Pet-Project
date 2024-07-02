package com.coolbank.service;

import com.coolbank.dto.AccountDTO;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO, UUID userId) {
        return null;
    }

    @Override
    public AccountDTO getAccountByAccountNumber(String number) {
        return null;
    }

    @Override
    public AccountDTO getAccountById(UUID accountId) {
        return null;
    }

    @Override
    public List<AccountDTO> getAllUserAccountsByUserId(UUID userId) {
        return null;
    }

    @Override
    public AccountDTO getAccountByUserId(UUID userId) {
        return null;
    }

    @Override
    public AccountDTO getAccountByHolderName(String holderName) {
        return null;
    }

    @Override
    public AccountDTO getBalanceByAccountId(UUID accountId) {
        return null;
    }

    @Override
    public AccountDTO getAccountByStatus(String accountStatus) {
        return null;
    }

    @Override
    public AccountDTO updateAccountById(UUID accountId, AccountDTO accountDTO) {
        return null;
    }

    @Override
    public AccountDTO updateAccountStatusById(UUID accountId, String status) {
        return null;
    }

    @Override
    public AccountDTO updateAccountBalanceById(UUID id, Double balance) {
        return null;
    }

    @Override
    public AccountDTO updateAccountBalanceByAccountNumber(String number, Double balance) {
        return null;
    }

    @Override
    public void deleteAccountByAccountId(UUID accountId) {

    }

    @Override
    public void deleteAccountByAccountNumber(String accountNumber) {

    }

    @Override
    public void deleteAllUserAccountsByUserId(UUID userId) {

    }
}
