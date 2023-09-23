package com.example.collections_backend.email.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    private void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public String createConformationToken(Long id, ConfirmationType type, String message) {
        String token = generateToken();

        var confirmationToken = ConfirmationToken.builder()
                .confirmationToken(token)
                .expiredTime(LocalDateTime.now().plusMinutes(15))
                .userId(id)
                .confirmationType(type)
                .message(message)
                .build();

        saveConfirmationToken(confirmationToken);
        return token;
    }

    public String createConformationToken(Long id, ConfirmationType type) {
        String token = generateToken();

        var confirmationToken = ConfirmationToken.builder()
                .confirmationToken(token)
                .expiredTime(LocalDateTime.now().plusMinutes(15))
                .userId(id)
                .confirmationType(type)
                .build();

        saveConfirmationToken(confirmationToken);
        return token;
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

}
