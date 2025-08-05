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
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import static sun.security.x509.X509CertInfo.KEY;

@Service
public class PaymentService {

    private final MerchantRepository merchantRepository;
    private final TransactionRepository transactionRepository;
    private final Random random = new Random();
    private static final int SHIFT = 6; //  move attribute for Ceaser Cypher

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

//Using ceaser cypher for masking the card number
private String maskCardNumber(String cardNumber) {
    if (cardNumber == null || cardNumber.length() < 4) {
        return cardNumber;
    }
    String lastFour = cardNumber.substring(cardNumber.length() - 4);
    String toEncrypt = cardNumber.substring(0, cardNumber.length() - 4);
    String encrypted = shiftEncrypt(toEncrypt);
    return encrypted + "-" + lastFour;
}

    public String unmaskCardNumber(String maskedCardNumber) {
        if (maskedCardNumber == null || !maskedCardNumber.contains("-")) {
            return maskedCardNumber;
        }
        String[] parts = maskedCardNumber.split("-");
        if (parts.length < 2) {
            return maskedCardNumber;
        }
        String encryptedPart = parts[0];
        String lastFour = parts[1];
        String decrypted = shiftDecrypt(encryptedPart);
        return decrypted + lastFour;
    }

    private String shiftEncrypt(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            // Shift each character by SHIFT (6) and wrap around using modulo 256 (full ASCII range)
            c = (char) ((c + SHIFT) % 256);
            result.append(c);
        }
        return result.toString();
    }

    private String shiftDecrypt(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            // Reverse shift by subtracting SHIFT, handling wrap-around
            c = (char) ((c - SHIFT + 256) % 256); // +256 ensures no negative values
            result.append(c);
        }
        return result.toString();
    }


    private boolean isValidExpiryDate(String expiryDate) {
        // Basic validation - should be 4 digits
        return expiryDate != null && expiryDate.matches("\\d{4}");
    }
}