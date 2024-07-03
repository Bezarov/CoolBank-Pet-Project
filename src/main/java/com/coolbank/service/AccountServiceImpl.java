package com.coolbank.service;

import com.coolbank.dto.AccountDTO;
import com.coolbank.model.Account;
import com.coolbank.model.Users;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UsersRepository usersRepository) {
        this.accountRepository = accountRepository;
        this.usersRepository = usersRepository;
    }

    private AccountDTO convertAccountModelToDTO(com.coolbank.model.Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountName(account.getAccountName());
        accountDTO.setAccountHolderFullName(account.getAccountHolderFullName());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setCreatedDate(account.getCreatedDate());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setCards(account.getCards());
        accountDTO.setStatus(account.getStatus());
        return accountDTO;
    }

    private Account convertAccountDTOToModel(AccountDTO accountDTO, UUID usersUUID) {
        Account account = new Account();
        account.setAccountName(accountDTO.getAccountHolderFullName());
        account.setAccountHolderFullName(accountDTO.getAccountHolderFullName());
        account.setAccountType(accountDTO.getAccountType());
        account.setCurrency(accountDTO.getCurrency());
        account.setId(UUID.randomUUID());
        account.setCreatedDate(LocalDateTime.now());
        account.setBalance(0.0);
        Users users = usersRepository.getById(usersUUID);
        account.setUser(users);
        return account;
    }

    @Override
    public AccountDTO createAccount(UUID userId, AccountDTO accountDTO) {
        return null;
    }

    @Override
    public AccountDTO getAccountByAccountName(String accountName) {
        return null;
    }

    @Override
    public AccountDTO getAccountById(UUID accountId) {
        return null;
    }

    @Override
    public AccountDTO getAccountByUserId(UUID userId) {
        return null;
    }

    @Override
    public List<AccountDTO> getAllUserAccountsByUserId(UUID userId) {
        return null;
    }

    @Override
    public AccountDTO getAccountByHolderFullName(String accountHolderFullName) {
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
    public AccountDTO updateAccountBalanceById(UUID accountId, Double balance) {
        return null;
    }

    @Override
    public AccountDTO updateAccountBalanceByAccountNumber(String accountNumber, Double balance) {
        return null;
    }

    @Override
    public AccountDTO updateAccountBalanceByAccountName(String accountName, Double balance) {
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
