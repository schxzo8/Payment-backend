package com.paymentapp.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Setter
@Entity
@Table(name = "merchants")
public class Merchant {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "merchant_name", nullable = false)
    private String merchantName;

    @Getter
    @Column(name = "merchant_id", unique = true, nullable = false)
    private String merchantId;

    @Getter
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @Getter
    @Column(nullable = false)
    private String password;

    //
    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();


    public List<Transaction> getTransactions() {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        return transactions;
    }

    // HELPER METHODS FOR BIDIRECTIONAL RELATIONSHIP
    public void addTransaction(Transaction transaction) {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
        transaction.setMerchant(this);
    }

    public void removeTransaction(Transaction transaction) {
        if (transactions != null) {
            transactions.remove(transaction);
            transaction.setMerchant(null);
        }
    }
}