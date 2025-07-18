package com.paymentapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String cardNumber; // Will be masked before storage

    @Column(nullable = false)
    private String cardExpiryDate; // Format: YYMM

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;
    private TransactionStatus transactionStatus;

    public Object getTransactionId() {
        return transactionId;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public Object getAmount() {
        return amount;
    }

    public Object getCardNumber() {
        return cardNumber;
    }

    public Object getCardExpiryDate() {
        return cardExpiryDate;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public Object getStatus() {
        return status;
    }

    public void setTransactionId(String s) {
        this.transactionId = transactionId;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public void setAmount(Object amount) {
        this.amount = (BigDecimal) amount;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}