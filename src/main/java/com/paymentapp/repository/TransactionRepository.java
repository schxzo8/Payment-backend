package com.paymentapp.repository;

import com.paymentapp.model.Merchant;
import com.paymentapp.model.Transaction;
import com.paymentapp.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByMerchant(Merchant merchant);
    Optional<Transaction> findByTransactionId(String transactionId);
    List<Transaction> findByStatus(TransactionStatus status);
}