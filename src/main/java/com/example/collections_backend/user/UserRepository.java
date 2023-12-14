package com.example.collections_backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByIdUser(Long id);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsernameIgnoreCase(String username);


    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :r, '%')) " +
            "OR LOWER(u.surname) LIKE LOWER(CONCAT('%', :r, '%')) " +
            "OR LOWER(u.name) LIKE LOWER(CONCAT('%', :r, '%'))")
    List<User> searchUsersByRequest(@Param("r") String request, Pageable pageable);

    @Query("SELECT COUNT(u) FROM User u WHERE " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :r, '%')) " +
            "OR LOWER(u.surname) LIKE LOWER(CONCAT('%', :r, '%')) " +
            "OR LOWER(u.name) LIKE LOWER(CONCAT('%', :r, '%'))")
    Long countUsersByRequest(@Param("r") String request);

}
