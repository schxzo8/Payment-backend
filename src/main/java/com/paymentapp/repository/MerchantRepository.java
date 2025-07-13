package com.paymentapp.repository;

import com.paymentapp.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByMerchantId(String merchantId);
    Optional<Merchant> findByEmail(String email);
    boolean existsByMerchantId(String merchantId);
    boolean existsByEmail(String email);
}