package com.example.collections_backend.collections;

import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Iterable<Collection> findAllByUser(User user);
    Iterable<Collection> findTop3ByUserAndIsPrivate(User user, boolean isPrivate);
    Optional<Collection> findByUserAndIdCollection(User user, Long id);
    Long countAllByUser(User user);
}
