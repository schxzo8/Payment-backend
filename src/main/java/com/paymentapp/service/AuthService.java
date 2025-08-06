package com.paymentapp.service;

import com.paymentapp.dto.LoginRequest;
import com.paymentapp.dto.LoginResponse;
import com.paymentapp.dto.MerchantRegistrationDto;
import com.paymentapp.exception.AppException;
import com.paymentapp.model.Merchant;
import com.paymentapp.repository.MerchantRepository;
import com.paymentapp.security.JwtTokenProvider;
import com.paymentapp.security.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(AuthenticationManager authenticationManager,
                       MerchantRepository merchantRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.merchantRepository = merchantRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public Merchant registerMerchant(MerchantRegistrationDto registrationDto) {
        // Check if email already exists
        if (merchantRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new AppException("Email address already in use");
        }

        // Check if merchant ID already exists
        if (merchantRepository.findByMerchantId(registrationDto.getMerchantId()).isPresent()) {
            throw new AppException("Merchant ID already in use");
        }

        // Create new merchant
        Merchant merchant = new Merchant();
        merchant.setMerchantName(registrationDto.getMerchantName());
        merchant.setMerchantId(registrationDto.getMerchantId());
        merchant.setEmail(registrationDto.getEmail());
        merchant.setPhoneNo(registrationDto.getPhoneNo());
        merchant.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        return merchantRepository.save(merchant);
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            return new LoginResponse(jwt);
        } catch (Exception e) {
            throw new AppException("Invalid username or password");
        }
    }
}