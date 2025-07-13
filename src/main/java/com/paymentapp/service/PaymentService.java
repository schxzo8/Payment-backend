package com.paymentapp.service;

import com.paymentapp.dto.PaymentRequest;
import com.paymentapp.dto.PaymentResponse;
import com.paymentapp.exception.AppException;
import com.paymentapp.exception.ResourceNotFoundException;
import com.paymentapp.model.Merchant;
import com.paymentapp.model.Transaction;
import com.paymentapp.model.TransactionStatus;
import com.paymentapp.repository.MerchantRepository;
import com.paymentapp.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    private final MerchantRepository merchantRepository;
    private final TransactionRepository transactionRepository;
    private final Random random = new Random();

    public PaymentService(MerchantRepository merchantRepository,
                          TransactionRepository transactionRepository) {
        this.merchantRepository = merchantRepository;
        this.transactionRepository = transactionRepository;
    }

    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Validate merchant exists
        Merchant merchant = merchantRepository.findByMerchantId(paymentRequest.getMerchantId())
                .orElseThrow(() -> new ResourceNotFoundException("Merchant", "merchantId", paymentRequest.getMerchantId()));

        // Validate card expiry (basic validation)
        if (!isValidExpiryDate(paymentRequest.getCardExpiryDate())) {
            throw new AppException("Invalid card expiry date");
        }

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setMerchant(merchant);
        transaction.setAmount(paymentRequest.getAmount());
        transaction.setCardNumber(maskCardNumber(paymentRequest.getCardNumber()));
        transaction.setCardExpiryDate(paymentRequest.getCardExpiryDate());
        transaction.setTimestamp(LocalDateTime.now());

        // Simulate payment processing (random success/failure)
        if (random.nextBoolean()) {
            transaction.setStatus(TransactionStatus.SUCCESS);
        } else {
            transaction.setStatus(TransactionStatus.FAILED);
        }

        Transaction savedTransaction = transactionRepository.save(transaction);

        PaymentResponse response = new PaymentResponse();
        response.setTransactionId(savedTransaction.getTransactionId());
        response.setStatus(savedTransaction.getStatus());
        response.setMessage("Payment processed with status: " + savedTransaction.getStatus());

        return response;
    }

    private String generateTransactionId() {
        return "TXN" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return cardNumber;
        }
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }

    private boolean isValidExpiryDate(String expiryDate) {
        // Basic validation - should be 4 digits
        return expiryDate != null && expiryDate.matches("\\d{4}");
    }
}