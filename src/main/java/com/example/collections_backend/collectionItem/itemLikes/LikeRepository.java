package com.example.collections_backend.collectionItem.itemLikes;

import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeItem, Long> {

    Optional<LikeItem> findByCollectionItemAndUser(CollectionItem collectionItem, User user);

    boolean existsByCollectionItemAndUser(CollectionItem collectionItem, User user);

    int countAllByCollectionItem(CollectionItem collectionItem);

}
