package com.example.collections_backend.collectionItem;

import com.example.collections_backend.collections.CollectionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {

    List<CollectionItem> findAllByCollectionEntity(CollectionEntity collectionEntity);

    Long countAllByCollectionEntity(CollectionEntity collectionEntity);

    Optional<CollectionItem> findTopByCollectionEntityOrderByCountIdDesc(CollectionEntity collectionEntity);

    Optional<CollectionItem> findByCollectionEntityAndCountId(CollectionEntity collectionEntity, Integer countId);

    List<CollectionItem> findAllByCollectionEntity_User_Username(String username, Pageable pageable);
    List<CollectionItem> findAllByCollectionEntity_IdCollection(Long idCollection, Pageable pageable);

    @Query("SELECT c FROM CollectionItem c WHERE " +
            "c.collectionEntity.user.username IN :users " +
            "AND c.collectionEntity.isPrivate = false " +
            "ORDER BY c.creationTime DESC")
    List<CollectionItem> findPosts(@Param("users") Collection<String> users, Pageable pageable);


    @Query("SELECT c FROM CollectionItem c WHERE " +
            /*"c.creationTime >= :date AND " +*/
            "c.collectionEntity.isPrivate = false " +
            "ORDER BY (SELECT COUNT(r) FROM c.likes r) DESC")
    List<CollectionItem> getPopularPostsBeforeDate(@Param("date") LocalDateTime date, Pageable pageable);

}
