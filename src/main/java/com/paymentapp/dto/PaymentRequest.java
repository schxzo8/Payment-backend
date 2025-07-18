package com.paymentapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRequest {
    @NotBlank
    private String merchantId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "^[0-9]{16}$")
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "^[0-9]{4}$")
    private String cardExpiryDate; // YYMM format

    public Object getAmount() {
        return amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public String getMerchantId() {
        return merchantId;
    }
}