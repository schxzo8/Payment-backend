package com.paymentapp.dto;

import com.paymentapp.model.TransactionStatus;
import lombok.Data;

@Data
public class PaymentResponse {
    private String transactionId;
    private TransactionStatus status;
    private String message;

    public void setStatus(Object status) {
        this.status = (TransactionStatus) status;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId.toString();
    }

    public void setMessage(String message) {
        this.message = message;
    }
}