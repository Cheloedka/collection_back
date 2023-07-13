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
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idToken;

    private String confirmationToken;
    private LocalDateTime expiredTime;
    private LocalDateTime confirmedTime;

    @ManyToOne
    @JoinColumn (
            nullable = false,
            name = "id_user"
    )
    private User user;
}
