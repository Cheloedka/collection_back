package com.example.collections_backend.collectionItem;

import com.example.collections_backend.collections.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {

    ArrayList<CollectionItem> findTop5ByCollectionEntity(CollectionEntity collectionEntity);
    Long countAllByCollectionEntity(CollectionEntity collectionEntity);
    Optional<CollectionItem> findTopByCollectionEntityOrderByCountIdDesc(CollectionEntity collectionEntity);
    Optional<CollectionItem> findByCollectionEntityAndAndCountId(CollectionEntity collectionEntity, Integer countId);

}
