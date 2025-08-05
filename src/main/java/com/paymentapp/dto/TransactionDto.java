package com.paymentapp.dto;

import com.paymentapp.model.TransactionStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private String transactionId;
    private String merchantId;
    private String merchantName;
    private BigDecimal amount;
    private String maskedCardNumber;
    private String cardExpiryDate;
    private LocalDateTime timestamp;
    private TransactionStatus status;
}