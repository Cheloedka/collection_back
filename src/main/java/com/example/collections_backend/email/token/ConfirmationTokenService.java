package com.example.collections_backend.email.token;

import com.example.collections_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public String createConformationToken(User user){
        String token = UUID.randomUUID().toString();

        var confirmationToken = ConfirmationToken.builder()
                .confirmationToken(token)
                .expiredTime(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        saveConfirmationToken(confirmationToken);
        return token;
    }

}
