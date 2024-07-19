package com.coolbank.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String accountName;
    private String accountHolderFullName;
    private BigDecimal balance;
    private String accountType;
    private LocalDateTime createdDate;
    private String status;
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Card> cards;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> outgoingPayments;

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> incomingPayments;

    public Account() {
    }

    public Account(UUID id, String accountName, String accountHolderFullName, BigDecimal balance,
                   String accountType, LocalDateTime createdDate, String status,
                   String currency, Users users, List<Card> cards) {
        this.id = id;
        this.accountName = accountName;
        this.accountHolderFullName = accountHolderFullName;
        this.balance = balance;
        this.accountType = accountType;
        this.createdDate = createdDate;
        this.status = status;
        this.currency = currency;
        this.users = users;
        this.cards = cards;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountHolderFullName() {
        return accountHolderFullName;
    }

    public void setAccountHolderFullName(String accountHolderName) {
        this.accountHolderFullName = accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", accountHolderFullName='" + accountHolderFullName + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", createdDate=" + createdDate +
                ", status='" + status + '\'' +
                ", currency='" + currency + '\'' +
                ", \n \t\t\tcards=" + cards +
                '}';
    }
}
