package com.example.collections_backend.email.token;

import com.example.collections_backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idToken;

    private String confirmationToken;
    private LocalDateTime expiredTime;
    private LocalDateTime confirmedTime;
    private String message;

    private Long userId;

    @Column
    @Enumerated(EnumType.STRING)
    private ConfirmationType confirmationType;
}
