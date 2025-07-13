package com.paymentapp.service;

import com.paymentapp.dto.TransactionDto;
import com.paymentapp.exception.ResourceNotFoundException;
import com.paymentapp.model.Merchant;
import com.paymentapp.model.Transaction;
import com.paymentapp.repository.MerchantRepository;
import com.paymentapp.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              MerchantRepository merchantRepository) {
        this.transactionRepository = transactionRepository;
        this.merchantRepository = merchantRepository;
    }

    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getTransactionsByMerchant(String merchantId) {
        Merchant merchant = merchantRepository.findByMerchantId(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant", "merchantId", merchantId));

        return transactionRepository.findByMerchant(merchant).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TransactionDto getTransactionById(String transactionId) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "transactionId", transactionId));
        return convertToDto(transaction);
    }

    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setMerchantId(transaction.getMerchant().getMerchantId());
        dto.setMerchantName(transaction.getMerchant().getMerchantName());
        dto.setAmount(transaction.getAmount());
        dto.setMaskedCardNumber(transaction.getCardNumber());
        dto.setCardExpiryDate(transaction.getCardExpiryDate());
        dto.setTimestamp(transaction.getTimestamp());
        dto.setStatus(transaction.getStatus());
        return dto;
    }
}