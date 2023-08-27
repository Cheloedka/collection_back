package com.example.collections_backend.friendship;

import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.exception_handling.exceptions.SomethingAlreadyExist;
import com.example.collections_backend.user.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserManagementService userManagementService;


    public String newFollowing(String username) {
        var follower = userManagementService.getCurrentUser();
        var user = userManagementService.getUserByUsername(username);

        if (isFollowingExist(username) )
            throw new SomethingAlreadyExist("You already following to this account");

        var friendship = Friendship.builder()
                .follower(follower)
                .user(user)
                .build();

        friendshipRepository.save(friendship);
        return "Success";

    }

    public boolean isFollowingExist(String username) {

        return friendshipRepository.existsFriendshipByFollowerAndUser(
                userManagementService.getCurrentUser(),
                userManagementService.getUserByUsername(username)
        );
    }

    public String deleteFollowing(String username) {
        var friendship = friendshipRepository.findFriendshipByFollowerAndUser(
                userManagementService.getCurrentUser(),
                userManagementService.getUserByUsername(username)
        ).orElseThrow(EntityNotFoundException::new);
        friendshipRepository.delete(friendship);

        return "success";
    }

}
