package com.coolbank.dto;

import com.coolbank.model.Account;

public class AccountDTO {
    private String accountNumber;
    private Double balance;



    public AccountDTO(String accountNumber, Double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public static AccountDTO mappToAccountDTO (Account account){
        return new AccountDTO(account.getAccountNumber(), account.getBalance());

    }

//    public static Account mappToAccount(AccountDTO accountDTO, Users users, String accountHolderName,
//                                        String accountType, LocalDateTime createdDate, String status,
//                                        String currency){
//        return new Account(
//                UUID.randomUUID(),
//                accountDTO.getAccountNumber(),
//                accountHolderName,
//                accountDTO.getBalance(),
//                accountType,
//                createdDate,
//                status,
//                currency,
//                users,
//                null
//        );
//    }
}
