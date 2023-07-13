package com.example.collections_backend.email.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByConfirmationToken(String token);
}
