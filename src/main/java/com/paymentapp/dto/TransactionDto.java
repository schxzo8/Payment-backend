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

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId.toString();
    }

    public void setMerchantId(Object merchantId){
        this.merchantId = merchantId.toString();
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName.toString();
    }

    public void setAmount(Object amount) {
        this.amount = (BigDecimal) amount;
    }

    public void setMaskedCardNumber(Object maskedCardNumber){
        this.maskedCardNumber = maskedCardNumber.toString();
    }

    public void setCardExpiryDate(Object cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate.toString();
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = LocalDateTime.parse(timestamp.toString());
    }

    public void setStatus(Object status) {
        this.status = (TransactionStatus) status;
    }
}