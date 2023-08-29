package com.example.collections_backend.collections;

import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {
    Iterable<CollectionEntity> findAllByUser(User user);
    Iterable<CollectionEntity> findTop3ByUserAndIsPrivate(User user, boolean isPrivate);
    Optional<CollectionEntity> findByUserAndIdCollection(User user, Long id);
    Long countAllByUser(User user);
}
