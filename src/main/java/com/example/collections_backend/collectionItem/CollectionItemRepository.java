package com.example.collections_backend.collectionItem;

import com.example.collections_backend.collections.CollectionEntity;
import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {

    ArrayList<CollectionItem> findTop5ByCollectionEntity(CollectionEntity collectionEntity);
    Long countAllByCollectionEntity(CollectionEntity collectionEntity);
}
