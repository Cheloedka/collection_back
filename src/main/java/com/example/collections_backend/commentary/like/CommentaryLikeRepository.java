package com.example.collections_backend.commentary.like;

import com.example.collections_backend.commentary.Commentary;
import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentaryLikeRepository extends JpaRepository<CommentaryLike, Long> {
    Optional<CommentaryLike> findByCommentaryAndIdUser(Commentary commentary, User user);
}
