package com.coolbank.service;

import com.coolbank.dto.AccountDTO;
import com.coolbank.model.Account;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UsersRepository usersRepository) {
        this.accountRepository = accountRepository;
        this.usersRepository = usersRepository;
    }

    private AccountDTO convertAccountModelToDTO(Account account) {
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

    private Account convertAccountDTOToModel(UUID usersId, AccountDTO accountDTO) {
        Account account = new Account();
        account.setAccountName(accountDTO.getAccountName());
        account.setBalance(accountDTO.getBalance());
        account.setAccountHolderFullName(accountDTO.getAccountHolderFullName());
        account.setStatus(accountDTO.getStatus());
        account.setAccountType(accountDTO.getAccountType());
        account.setCreatedDate(LocalDateTime.now());
        account.setCurrency(accountDTO.getCurrency());
        account.setUser(usersRepository.findById(usersId).orElseThrow(() ->
                new RuntimeException("User ID Not Found in Database")));
        return account;
    }

    @Override
    public ResponseEntity<String> createAccount(UUID userId, AccountDTO accountDTO) {
        usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + userId));

        accountRepository.save(convertAccountDTOToModel(userId, accountDTO));
        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
    }

    @Override
    public AccountDTO getAccountByAccountName(String accountName) {
        return accountRepository.findByAccountName(accountName)
                .map(this::convertAccountModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such NAME was NOT Found" + accountName));
    }

    @Override
    public AccountDTO getAccountById(UUID accountId) {
        return accountRepository.findById(accountId)
                .map(this::convertAccountModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
    }

    @Override
    public List<AccountDTO> getAllUserAccountsByUserId(UUID userId) {
        usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + userId));

        List<Account> accounts = accountRepository.findAllByUsersId(userId);
        return accounts.stream()
                .map(this::convertAccountModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountByHolderFullName(String accountHolderFullName) {
        return accountRepository.findByAccountHolderFullName(accountHolderFullName)
                .map(this::convertAccountModelToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account with such Holder Full Name was NOT Found" + accountHolderFullName
                ));
    }

    @Override
    public Double getBalanceByAccountId(UUID accountId) {
        return accountRepository.findById(accountId)
                .map(Account::getBalance)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
    }

    @Override
    public List<AccountDTO> getAccountsByStatus(UUID userId, String accountStatus) {
        usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + userId));

        List<Account> accounts = accountRepository.findAllByUsersId(userId);
        return accounts.stream()
                .filter(account -> account.getStatus().equals(accountStatus))
                .map(this::convertAccountModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO updateAccountById(UUID accountId, AccountDTO accountDTO) {
        return accountRepository.findById(accountId)
                .map(EntityAccount -> {
                    EntityAccount.setAccountName(accountDTO.getAccountName());
                    EntityAccount.setAccountHolderFullName(accountDTO.getAccountHolderFullName());
                    EntityAccount.setStatus(accountDTO.getStatus());
                    EntityAccount.setAccountType(accountDTO.getAccountType());
                    EntityAccount.setCurrency(accountDTO.getCurrency());
                    accountRepository.save(EntityAccount);
                    return convertAccountModelToDTO(EntityAccount);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
    }

    @Override
    public AccountDTO updateAccountStatusById(UUID accountId, String status) {
        return accountRepository.findById(accountId)
                .map(EntityAccount -> {
                    EntityAccount.setStatus(status);
                    accountRepository.save(EntityAccount);
                    return convertAccountModelToDTO(EntityAccount);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
    }

    @Override
    public AccountDTO updateAccountBalanceById(UUID accountId, Double balance) {
        return accountRepository.findById(accountId)
                .map(EntityAccount -> {
                    EntityAccount.setBalance(balance);
                    accountRepository.save(EntityAccount);
                    return convertAccountModelToDTO(EntityAccount);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
    }

    @Override
    public AccountDTO updateAccountBalanceByAccountName(String accountName, Double balance) {
        return accountRepository.findByAccountName(accountName)
                .map(EntityAccount -> {
                    EntityAccount.setBalance(balance);
                    accountRepository.save(EntityAccount);
                    return convertAccountModelToDTO(EntityAccount);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account with such Account Name was NOT Found" + accountName));
    }

    @Override
    public void deleteAccountByAccountId(UUID accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
        accountRepository.deleteById(accountId);
    }

    @Override
    public void deleteAccountByAccountName(String accountName) {
        accountRepository.findByAccountName(accountName)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountName));
        accountRepository.deleteByAccountName(accountName);
    }

    @Override
    public void deleteAllUserAccountsByUserId(UUID userId) {
        usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + userId));

        accountRepository.deleteAllByUsersId(userId);
    }
}
