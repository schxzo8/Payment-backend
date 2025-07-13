package com.paymentapp.controller;

import com.paymentapp.dto.TransactionDto;
import com.paymentapp.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByMerchant(
            @PathVariable String merchantId) {
        return ResponseEntity.ok(transactionService.getTransactionsByMerchant(merchantId));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionById(
            @PathVariable String transactionId) {
        return ResponseEntity.ok(transactionService.getTransactionById(transactionId));
    }
}