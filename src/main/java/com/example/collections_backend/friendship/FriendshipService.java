package com.example.collections_backend.friendship;

import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserService userService;


    public String newFollowing(String username) {
        var follower = userService.getCurrentUser();
        var user = userService.getUserByUsername(username);

        var friendship = Friendship.builder()
                .follower(follower)
                .user(user)
                .build();

        friendshipRepository.save(friendship);
        return "Success";

    }

    public boolean isFollowingExist(String username) {

        return friendshipRepository.existsFriendshipByFollowerAndUser(
                userService.getCurrentUser(),
                userService.getUserByUsername(username)
        );
    }

    public String deleteFollowing(String username) {
        var friendship = friendshipRepository.findFriendshipByFollowerAndUser(
                userService.getCurrentUser(),
                userService.getUserByUsername(username)
        ).orElseThrow(EntityNotFoundException::new);
        friendshipRepository.delete(friendship);

        return "success";
    }

}
