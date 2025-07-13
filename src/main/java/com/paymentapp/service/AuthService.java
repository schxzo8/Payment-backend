package com.paymentapp.service;

import com.paymentapp.dto.LoginRequest;
import com.paymentapp.dto.LoginResponse;
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

        // Create hardcoded merchant user
        createHardcodedMerchant();
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new LoginResponse(jwt);
    }

    private void createHardcodedMerchant() {
        if (!merchantRepository.existsByEmail("merchant")) {
            Merchant merchant = new Merchant();
            merchant.setMerchantName("Default Merchant");
            merchant.setMerchantId("MERCHANT123");
            merchant.setEmail("merchant");
            merchant.setPhoneNo("1234567890");
            merchant.setPassword(passwordEncoder.encode("pay123"));

            merchantRepository.save(merchant);
        }
    }
}