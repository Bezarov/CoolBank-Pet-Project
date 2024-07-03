package com.coolbank.dto;

import com.coolbank.model.Account;

import java.time.LocalDate;

public class CardDTO {
    private String cardNumber;
    private String cardHolderFullName;
    private LocalDate expirationDate;
    private String cvv;
    private Account account;
    private String status;

    public CardDTO() {
    }

    public CardDTO(String cardNumber, String cardHolderFullName,
                   LocalDate expirationDate, String cvv, Account account, String status) {
        this.cardNumber = cardNumber;
        this.cardHolderFullName = cardHolderFullName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.account = account;
        this.status = status;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderFullName() {
        return cardHolderFullName;
    }

    public void setCardHolderFullName(String cardHolderFullName) {
        this.cardHolderFullName = cardHolderFullName;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
