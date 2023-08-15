package com.example.collections_backend.email.token;

import com.example.collections_backend.user.User;
import com.example.collections_backend.user.UserService;
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
        String token = UUID.randomUUID().toString();

        var confirmationToken = ConfirmationToken.builder()
                .confirmationToken(token)
                .expiredTime(LocalDateTime.now().plusMinutes(15))
                .userId(id)
                .confirmationType(type)
                .message(message)
                .build();
        System.out.println(confirmationToken);

        saveConfirmationToken(confirmationToken);
        return token;
    }

}
