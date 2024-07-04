package com.coolbank.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String cardNumber;
    private String cardHolderFullName;
    private UUID cardHolderUUID;
    private LocalDate expirationDate;
    private String cvv;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;
    private String status;

    public Card() {
    }

    public Card(UUID id, String cardNumber, String cardHolderFullName,
                UUID cardHolderUUID, LocalDate expirationDate,
                String cvv, Account account, String status) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolderFullName = cardHolderFullName;
        this.cardHolderUUID = cardHolderUUID;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.account = account;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setCardHolderFullName(String cardHolderName) {
        this.cardHolderFullName = cardHolderName;
    }

    public UUID getCardHolderUUID() {
        return cardHolderUUID;
    }

    public void setCardHolderUUID(UUID cardHolderUUID) {
        this.cardHolderUUID = cardHolderUUID;
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
