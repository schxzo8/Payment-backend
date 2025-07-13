package com.paymentapp.dto;

import com.paymentapp.model.TransactionStatus;
import lombok.Data;

@Data
public class PaymentResponse {
    private String transactionId;
    private TransactionStatus status;
    private String message;
}