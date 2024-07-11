package com.coolbank.service;

import com.coolbank.dto.AccountDTO;
import com.coolbank.model.Account;
import com.coolbank.repository.AccountRepository;
import com.coolbank.repository.CardRepository;
import com.coolbank.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UsersRepository usersRepository;
    private final CardRepository cardRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              UsersRepository usersRepository, CardRepository cardRepository) {
        this.accountRepository = accountRepository;
        this.usersRepository = usersRepository;
        this.cardRepository = cardRepository;
    }

    private AccountDTO convertAccountModelToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountName(account.getAccountName());
        accountDTO.setAccountHolderFullName(account.getAccountHolderFullName());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setCreatedDate(account.getCreatedDate());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setCurrency(account.getCurrency());
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
    public List<AccountDTO> getAllAccountByHolderFullName(String accountHolderFullName) {
        usersRepository.findByFullName(accountHolderFullName).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such Full Name was NOT Found" + accountHolderFullName));

        List<Account> accounts = accountRepository.findByAccountHolderFullName(accountHolderFullName);
        return accounts.stream()
                .map(this::convertAccountModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getBalanceByAccountId(UUID accountId) {
        return accountRepository.findById(accountId)
                .map(Account::getBalance)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
    }

    @Override
    public List<AccountDTO> getAllAccountsByStatus(UUID userId, String accountStatus) {
        usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + userId));

        List<Account> accounts = accountRepository.findAllByUsersId(userId);
        return accounts.stream()
                .filter(account -> account.getStatus().equals(accountStatus))
                .map(this::convertAccountModelToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO refillAccount(UUID accountId, BigDecimal amount) {
        return accountRepository.findById(accountId)
                .map(EntityAccount -> {
                    EntityAccount.setBalance(EntityAccount.getBalance().add(amount));
                    accountRepository.save(EntityAccount);
                    return convertAccountModelToDTO(EntityAccount);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
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
    public AccountDTO updateAccountBalanceById(UUID accountId, BigDecimal newBalance) {
        return accountRepository.findById(accountId)
                .map(EntityAccount -> {
                    EntityAccount.setBalance(newBalance);
                    accountRepository.save(EntityAccount);
                    return convertAccountModelToDTO(EntityAccount);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
    }

    @Override
    public AccountDTO updateAccountBalanceByAccountName(String accountName, BigDecimal balance) {
        return accountRepository.findByAccountName(accountName)
                .map(EntityAccount -> {
                    EntityAccount.setBalance(balance);
                    accountRepository.save(EntityAccount);
                    return convertAccountModelToDTO(EntityAccount);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account with such Account Name was NOT Found" + accountName));
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteAccountByAccountId(UUID accountId) {
        accountRepository.findById(accountId)
                .map(EntityAccount -> {
                    EntityAccount.setStatus("PRE-REMOVED");
                    return accountRepository.save(EntityAccount);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such ID was NOT Found" + accountId));
        cardRepository.findAllByAccountId(accountId).forEach(EntityCard -> {
            EntityCard.setStatus("DEACTIVATED");
            cardRepository.save(EntityCard);
        });
        return new ResponseEntity<>("Account deleted successfully", HttpStatus.ACCEPTED);
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteAccountByAccountName(String accountName) {
        accountRepository.findByAccountName(accountName)
                .map(EntityAccount -> {
                    EntityAccount.setStatus("PRE-REMOVED");
                    return accountRepository.save(EntityAccount);
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account with such Account Name was NOT Found" + accountName));
        cardRepository.findAllByAccountId(accountRepository.findByAccountName(accountName).get().getId())
                .forEach(EntityCard -> {
                    EntityCard.setStatus("DEACTIVATED");
                    cardRepository.save(EntityCard);
                });
        return new ResponseEntity<>("Account deleted successfully", HttpStatus.ACCEPTED);
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteAllUserAccountsByUserId(UUID userId) {
        usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User with such ID was NOT Found" + userId));

        accountRepository.findAllByUsersId(userId).forEach(EntityAccount -> {
            EntityAccount.setStatus("PRE-REMOVED");
            cardRepository.findAllByAccountId(EntityAccount.getId()).forEach(EntityCard -> {
                EntityCard.setStatus("DEACTIVATED");
                cardRepository.save(EntityCard);
            });
            accountRepository.save(EntityAccount);
        });
        return new ResponseEntity<>("Accounts deleted successfully", HttpStatus.ACCEPTED);
    }
}
