package com.paymentapp.service;

import com.paymentapp.dto.MerchantDto;
import com.paymentapp.exception.ResourceNotFoundException;
import com.paymentapp.model.Merchant;
import com.paymentapp.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public List<MerchantDto> getAllMerchants() {
        return merchantRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MerchantDto getMerchantById(String merchantId) {
        Merchant merchant = merchantRepository.findByMerchantId(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant", "merchantId", merchantId));
        return convertToDto(merchant);
    }

    public MerchantDto createMerchant(MerchantDto merchantDto) {
        Merchant merchant = new Merchant();
        merchant.setMerchantName(merchantDto.getMerchantName());
        merchant.setMerchantId(merchantDto.getMerchantId());
        merchant.setEmail(merchantDto.getEmail());
        merchant.setPhoneNo(merchantDto.getPhoneNo());

        Merchant savedMerchant = merchantRepository.save(merchant);
        return convertToDto(savedMerchant);
    }

    public MerchantDto updateMerchant(String merchantId, MerchantDto merchantDto) {
        Merchant merchant = merchantRepository.findByMerchantId(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant", "merchantId", merchantId));

        merchant.setMerchantName(merchantDto.getMerchantName());
        merchant.setEmail(merchantDto.getEmail());
        merchant.setPhoneNo(merchantDto.getPhoneNo());

        Merchant updatedMerchant = merchantRepository.save(merchant);
        return convertToDto(updatedMerchant);
    }

    public void deleteMerchant(String merchantId) {
        Merchant merchant = merchantRepository.findByMerchantId(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant", "merchantId", merchantId));
        merchantRepository.delete(merchant);
    }

    private MerchantDto convertToDto(Merchant merchant) {
        MerchantDto dto = new MerchantDto();
        dto.setMerchantName(merchant.getMerchantName());
        dto.setMerchantId(merchant.getMerchantId());
        dto.setEmail(merchant.getEmail());
        dto.setPhoneNo(merchant.getPhoneNo());
        return dto;
    }
}