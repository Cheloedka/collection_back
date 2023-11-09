package com.example.collections_backend.friendship;

import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsFriendshipByFollowerAndUser(User follower, User user);
    Optional<Friendship> findFriendshipByFollowerAndUser(User follower, User user);
    Optional<Long> countAllByFollower(User user);

    List<Friendship> findTop4ByFollower(User user);

    List<Friendship> findAllByFollower(User user); //get all following
    List<Friendship> findAllByUser(User user);  //get all followers

}
