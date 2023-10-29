package com.example.collections_backend.user;

import com.example.collections_backend.collections.CollectionRepository;
import com.example.collections_backend.dto.userDto.*;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.exception_handling.exceptions.SomethingAlreadyExist;
import com.example.collections_backend.files.FileService;
import com.example.collections_backend.friendship.FriendshipRepository;
import com.example.collections_backend.friendship.FriendshipService;
import com.example.collections_backend.utils.ConsumerFunctions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CollectionRepository collectionRepository;
    private final FriendshipRepository friendshipRepository;
    private final FriendshipService friendshipService;
    private final FileService fileService;
    private final UserManagementService userManagementService;


    private User getUserByUsername(String username) {
        return  userManagementService.getUserByUsername(username);
    }

    public UserPageDto getUserPageInfo(String username){

        var user = getUserByUsername(username);

        return UserPageDto.builder()
                .username(username)
                .name(user.getName())
                .surname(user.getSurname())
                .image(user.getImage())
                .backgroundImage(user.getBackgroundImage())
                .countCollections(collectionRepository.countAllByUser(user))
                .collections(collectionRepository.findTop3ByUserAndIsPrivateOrderByIdCollection(user, false))
                .countFriendships(friendshipRepository
                        .countAllByFollower(user)
                        .orElseThrow(EntityNotFoundException::new))
                .friendships(friendshipRepository.findTop4ByFollower(user))
                .isFollower(friendshipService.isFollowingExist(username))
                .build();
    }

    public UserSettingsDto getUserSettingsInfo(String username){

        var user = getUserByUsername(username);


        return UserSettingsDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    public UserNavInfoDto getUserNavInfo() {

        var user = userManagementService.getCurrentUser();

        return UserNavInfoDto.builder()
                .username(user.getNickname())
                .image(user.getImage())
                .build();
    }

    public String editAccountSettings(String username, AccountSettingsEditDto request) throws IOException {

        var user = getUserByUsername(username);

        ConsumerFunctions.setIfNotNull(request.getName(), user::setName);
        ConsumerFunctions.setIfNotNull(request.getSurname(), user::setSurname);
        ConsumerFunctions.setIfNotNull(request.getNickname(), nick -> {
            if (userRepository.existsUserByUsername(request.getNickname()))
                throw new SomethingAlreadyExist("Username Already Exist");
            else
                user.setUsername(request.getNickname());
        });

        if (request.getImage() != null) {
            fileService.deleteImageFromStorage(user.getImage());
            user.setImage(fileService.uploadFile(request.getImage()));
        }

        userRepository.save(user);

        return "Changed";
    }

}
