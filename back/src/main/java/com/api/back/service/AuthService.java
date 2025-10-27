package com.api.back.service;

import java.security.GeneralSecurityException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.api.back.config.JwtUtils;
import com.api.back.model.User;
import com.api.back.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;

@Service
public class AuthService {

    private final WebClient webClient = WebClient.create();
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository,@Value("${app.googleClientId}") String clientId,JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        
        this.jwtUtils = jwtUtils;
    }

    
    public String authenticateWithGoogle(String token) {
        String googleTokenInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + token;
        Map<String, Object> tokenInfo;
        try {
            tokenInfo = webClient.get()
                    .uri(googleTokenInfoUrl)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            HttpStatus status = HttpStatus.valueOf(e.getStatusCode().value());
            switch (status) {
                case BAD_REQUEST -> throw new IllegalArgumentException("Invalid token format");
                case UNAUTHORIZED -> throw new IllegalArgumentException("Token expired or invalid");
                default -> throw new RuntimeException("Google authentication error: " + e.getStatusText(), e);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }

        if (tokenInfo == null) {
            throw new IllegalArgumentException("Invalid Google token");
        }

        String email = (String) tokenInfo.get("email");
        String firstName = (String) tokenInfo.get("given_name");
        String lastName = (String) tokenInfo.get("family_name");
        String imageUrl = (String) tokenInfo.get("picture");

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email not found in token");
        }

        try {
            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setEmail(email);
                        newUser.setFirstName(firstName);
                        newUser.setLastName(lastName);
                        newUser.setImageUrl(imageUrl);
                        return userRepository.save(newUser);
                    });
            return jwtUtils.createToken(user, false);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process user data", e);
        }
    }

    
    
    

}
