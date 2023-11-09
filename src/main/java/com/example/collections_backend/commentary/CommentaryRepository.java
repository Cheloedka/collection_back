package com.example.collections_backend.commentary;

import com.example.collections_backend.collectionItem.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
    List<Commentary> findAllByAnswerToItemAndAnswerToIdNull(CollectionItem item);
    List<Commentary> findAllByAnswerToId(Commentary commentary);
}
