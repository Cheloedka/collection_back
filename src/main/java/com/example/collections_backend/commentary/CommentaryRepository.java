package com.example.collections_backend.commentary;

import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {

    List<Commentary> findAllByAnswerToItemAndAnswerToIdNull(CollectionItem item);

    List<Commentary> findAllByAnswerToId(Commentary commentary);

    Long countAllByAnswerToItem(CollectionItem item);

    @Modifying
    @Query("UPDATE Commentary c SET c.author = null " +
            "WHERE c.author = :u " +
            "AND c.answerToItem.collectionEntity.user <> :u")
    void updateCommentaries(@Param("u") User user);
}
