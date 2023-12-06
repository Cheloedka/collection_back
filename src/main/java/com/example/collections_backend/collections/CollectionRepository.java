package com.example.collections_backend.collections;

import com.example.collections_backend.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {

    List<CollectionEntity> findAllByUser(User user);

    List<CollectionEntity> findAllByUserAndIsPrivateFalse(User user);

    List<CollectionEntity> findTop3ByUserAndIsPrivateFalseOrderByIdCollection(User user);

    Optional<CollectionEntity> findByUserAndIdCollection(User user, Long id);

    Long countAllByUserAndIsPrivateFalse(User user);

    @Query("SELECT c FROM CollectionEntity c WHERE " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :r, '%')) ")
    List<CollectionEntity> searchCollectionsEntitiesByRequest(@Param("r") String request, Pageable pageable);

    @Query("SELECT COUNT(c) FROM CollectionEntity c WHERE " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :r, '%')) ")
    Long countCollectionsEntitiesByRequest(@Param("r") String request);
}
