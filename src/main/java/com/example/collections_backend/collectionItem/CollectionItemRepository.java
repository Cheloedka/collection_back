package com.example.collections_backend.collectionItem;

import com.example.collections_backend.collections.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {

    List<CollectionItem> findAllByCollectionEntity(CollectionEntity collectionEntity);

    ArrayList<CollectionItem> findTop5ByCollectionEntity(CollectionEntity collectionEntity);

    Long countAllByCollectionEntity(CollectionEntity collectionEntity);

    Optional<CollectionItem> findTopByCollectionEntityOrderByCountIdDesc(CollectionEntity collectionEntity);

    Optional<CollectionItem> findByCollectionEntityAndCountId(CollectionEntity collectionEntity, Integer countId);

    List<CollectionItem> findAllByCollectionEntity_User_Username(String username);

}
