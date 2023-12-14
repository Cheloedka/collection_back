package com.example.collections_backend.friendship;

import com.example.collections_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsFriendshipByFollowerAndFollowing(User follower, User following);

    Optional<Friendship> findFriendshipByFollowerAndFollowing(User follower, User following);

    Optional<Long> countAllByFollower(User user);

    List<Friendship> findTop5ByFollower(User user);

    List<Friendship> findAllByFollower(User user); //get all following

    List<Friendship> findAllByFollowing(User user);  //get all followers

}
