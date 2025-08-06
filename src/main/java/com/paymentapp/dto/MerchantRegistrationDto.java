package com.paymentapp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MerchantRegistrationDto {
    // Getters and setters for all fields
    private String merchantName;
    private String merchantId;
    private String email;
    private String phoneNo;
    private String password;

    public MerchantRegistrationDto(String merchantName, String merchantId, String email, String phoneNo, String password) {
        this.merchantName = merchantName;
        this.merchantId = merchantId;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
    }

}