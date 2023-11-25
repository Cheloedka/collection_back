package com.example.collections_backend.email.token;

import com.example.collections_backend.user.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserManagementService userManagementService;

    private void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public String createConformationToken(Long id, ConfirmationType type, String message) {
        String token = generateToken();

        var confirmationToken = ConfirmationToken.builder()
                .confirmationToken(token)
                .expiredTime(LocalDateTime.now().plusMinutes(15))
                .user(userManagementService.getUserById(id))
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
                .user(userManagementService.getUserById(id))
                .confirmationType(type)
                .build();

        saveConfirmationToken(confirmationToken);
        return token;
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

}
