package com.coolbank.dto;

import com.coolbank.model.Card;

import java.time.LocalDateTime;
import java.util.List;

public class AccountDTO {
    private String accountName;
    private Double balance;
    private String accountHolderFullName;
    private String status;
    private String accountType;
    private LocalDateTime createdDate;
    private String currency;
    private List<Card> cards;

    public AccountDTO() {
    }

    public AccountDTO(String accountName, Double balance, String accountHolderFullName,
                      String status, String accountType, LocalDateTime createdDate,
                      String currency, List<Card> cards) {
        this.accountName = accountName;
        this.balance = balance;
        this.accountHolderFullName = accountHolderFullName;
        this.status = status;
        this.accountType = accountType;
        this.createdDate = createdDate;
        this.currency = currency;
        this.cards = cards;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAccountHolderFullName() {
        return accountHolderFullName;
    }

    public void setAccountHolderFullName(String accountHolderFullName) {
        this.accountHolderFullName = accountHolderFullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
