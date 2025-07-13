package com.paymentapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MerchantDto {
    @NotBlank
    private String merchantName;

    @NotBlank
    private String merchantId;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10,15}$")
    private String phoneNo;
}