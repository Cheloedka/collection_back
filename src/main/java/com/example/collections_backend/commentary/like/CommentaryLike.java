package com.example.collections_backend.commentary.like;

import com.example.collections_backend.commentary.Commentary;
import com.example.collections_backend.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "_commentary_like")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class CommentaryLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @CreatedBy
    private User idUser;

    @ManyToOne
    @JoinColumn(name = "commentary")
    private Commentary commentary;

    @Enumerated(EnumType.STRING)
    private LikeType likeType;

    @Builder
    public CommentaryLike(Commentary commentary, LikeType likeType) {
        this.commentary = commentary;
        this.likeType = likeType;
    }

}
