package com.example.collections_backend.friendship;

import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    boolean existsFriendshipByFollowerAndUser(User follower, User user);
    Optional<Friendship> findFriendshipByFollowerAndUser(User follower, User user);

    Iterable<Friendship> findTop4ByFollower(User user);
    Optional<Long> countAllByFollower(User user);

}
