package com.example.collections_backend.collections;

import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {

    List<CollectionEntity> findAllByUser(User user);
    List<CollectionEntity> findAllByUserAndIsPrivate(User user, boolean isPrivate);
    List<CollectionEntity> findTop3ByUserAndIsPrivateOrderByIdCollection(User user, boolean isPrivate);
    Optional<CollectionEntity> findByUserAndIdCollection(User user, Long id);
    Long countAllByUserAndIsPrivate(User user, boolean isPrivate);
}
