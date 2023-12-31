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
    private String message;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    @Column
    @Enumerated(EnumType.STRING)
    private ConfirmationType confirmationType;
}
