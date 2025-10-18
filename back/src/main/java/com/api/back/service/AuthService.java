package com.api.back.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthService {

    private final WebClient webClient = WebClient.create();

    public Object authenticateWithGoogle(String token) {
        String googleTokenInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + token;
        Map<String, Object> tokenInfo = webClient.get()
                .uri(googleTokenInfoUrl)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (tokenInfo == null || tokenInfo.get("email") == null) {
            throw new RuntimeException("Invalid Google token");
        }

        String email = (String) tokenInfo.get("email");
        String firstName = (String) tokenInfo.get("given_name");
        String lastName = (String) tokenInfo.get("family_name");
        String imageUrl = (String) tokenInfo.get("picture");


        return tokenInfo;
    }

}
