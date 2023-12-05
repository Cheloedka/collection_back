package com.example.collections_backend.collections;

import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {

    List<CollectionEntity> findAllByUser(User user);

    List<CollectionEntity> findAllByUserAndIsPrivateFalse(User user);

    List<CollectionEntity> findTop3ByUserAndIsPrivateFalseOrderByIdCollection(User user);

    Optional<CollectionEntity> findByUserAndIdCollection(User user, Long id);

    Long countAllByUserAndIsPrivateFalse(User user);
}
