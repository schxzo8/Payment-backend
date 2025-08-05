package com.paymentapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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
    private String cardNumber;

    @Column(nullable = false)
    private String cardExpiryDate;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;


    // Custom setter to maintain bidirectional relationship
    public void setMerchant(Merchant merchant) {
        if (!Objects.equals(this.merchant, merchant)) {
            if (this.merchant != null) {
                this.merchant.getTransactions().remove(this);
            }
            this.merchant = merchant;
            if (merchant != null && merchant.getTransactions() != null) {
                merchant.getTransactions().add(this);
            }
        }
    }

}