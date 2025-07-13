package com.paymentapp.controller;

import com.paymentapp.dto.MerchantDto;
import com.paymentapp.service.MerchantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping
    public ResponseEntity<List<MerchantDto>> getAllMerchants() {
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<MerchantDto> getMerchant(@PathVariable String merchantId) {
        return ResponseEntity.ok(merchantService.getMerchantById(merchantId));
    }

    @PostMapping
    public ResponseEntity<MerchantDto> createMerchant(@RequestBody MerchantDto merchantDto) {
        MerchantDto createdMerchant = merchantService.createMerchant(merchantDto);
        return new ResponseEntity<>(createdMerchant, HttpStatus.CREATED);
    }

    @PutMapping("/{merchantId}")
    public ResponseEntity<MerchantDto> updateMerchant(
            @PathVariable String merchantId,
            @RequestBody MerchantDto merchantDto) {
        return ResponseEntity.ok(merchantService.updateMerchant(merchantId, merchantDto));
    }

    @DeleteMapping("/{merchantId}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable String merchantId) {
        merchantService.deleteMerchant(merchantId);
        return ResponseEntity.noContent().build();
    }
}