package com.example.collections_backend.collectionItem.itemImages;

import com.example.collections_backend.collectionItem.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImagesItemRepository extends JpaRepository<ImagesItem, Long> {
    Optional<ImagesItem> findTop1ByCollectionItem(CollectionItem collectionItem);

    List<ImagesItem> findAllByCollectionItem(CollectionItem collectionItem);
}
