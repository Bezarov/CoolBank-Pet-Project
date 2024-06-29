package com.coolbank.dto;

import com.coolbank.model.Account;
import com.coolbank.model.Payment;

import java.time.LocalDateTime;

public class PaymentDTO {
    private Double amount;
    private Account fromAccount;
    private Account toAccount;
    private LocalDateTime paymentDate;
    private String status;

    public PaymentDTO(Double amount, Account fromAccount, Account toAccount, LocalDateTime paymentDate, String status) {
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static PaymentDTO mappToPaymentDTO(Payment payment){
        return new PaymentDTO(payment.getAmount(),
                payment.getFromAccount(), payment.getToAccount(),
                payment.getPaymentDate(), payment.getStatus());
    }
}
