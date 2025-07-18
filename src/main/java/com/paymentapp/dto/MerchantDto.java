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

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMerchantName(){
        return merchantName;
    }

    public String getEmail() {
        return email;
    }


    public String getPhoneNo() {
        return phoneNo;
    }

    public String getMerchantId() {
        return merchantId;
    }
}