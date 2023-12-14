package com.example.collections_backend.commentary.like;

import com.example.collections_backend.commentary.Commentary;
import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentaryLikeRepository extends JpaRepository<CommentaryLike, Long> {

    Optional<CommentaryLike> findByCommentaryAndIdUser(Commentary commentary, User user);

    Integer countAllByCommentaryAndLikeType(Commentary commentary, LikeType likeType);

    @Modifying
    @Query("UPDATE CommentaryLike l SET l.idUser = null " +
            "WHERE l.idUser = :u " +
            "AND l.commentary.answerToItem.collectionEntity.user <> :u")
    void updateLikes(@Param("u") User user);
}
