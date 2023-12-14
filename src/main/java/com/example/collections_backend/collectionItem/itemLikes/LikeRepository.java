package com.example.collections_backend.collectionItem.itemLikes;

import com.example.collections_backend.collectionItem.CollectionItem;
import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeItem, Long> {

    Optional<LikeItem> findByCollectionItemAndUser(CollectionItem collectionItem, User user);

    boolean existsByCollectionItemAndUser(CollectionItem collectionItem, User user);

    int countAllByCollectionItem(CollectionItem collectionItem);

    @Modifying
    @Query("UPDATE LikeItem l SET l.user = null " +
            "WHERE l.user = :u " +
            "AND l.collectionItem.collectionEntity.user <> :u")
    void updateLikes(@Param("u") User user);

}
