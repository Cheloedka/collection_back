package com.example.collections_backend.user;

import com.example.collections_backend.collections.CollectionRepository;
import com.example.collections_backend.dto.userDto.*;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.exception_handling.exceptions.SomethingAlreadyExist;
import com.example.collections_backend.exception_handling.exceptions.UserNotFoundException;
import com.example.collections_backend.files.FileService;
import com.example.collections_backend.friendship.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.PasswordAuthentication;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CollectionRepository collectionRepository;
    private final FriendshipRepository friendshipRepository;
    private final FileService fileService;

    public void confirmAccount(Long id) {
        var user = userRepository.findUserByIdUser(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setIsEnabled(true);
        userRepository.save(user);

    }

    public User getCurrentUser() {
        return userRepository.findUserByEmail( SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UserNotFoundException("User is not found"));
    }
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public UserPageDto getUserPageInfo(String username){
        var user = getUserByUsername(username);
        return UserPageDto.builder()
                .username(user.getNickname())
                .name(user.getName())
                .surname(user.getSurname())
                .image(user.getImage())
                .backgroundImage(user.getBackgroundImage())
                .countCollections(collectionRepository.countAllByUser(user))
                .collections(collectionRepository.findTop3ByUserAndIsPrivate(user, false))
                .countFriendships(friendshipRepository
                        .countAllByFollower(user)
                        .orElseThrow(EntityNotFoundException::new))
                .friendships(friendshipRepository.findTop4ByFollower(user))
                .build();
    }

    public UserSettingsDto getUserSettingsInfo(String username){
        var user = getUserByUsername(username);
        return UserSettingsDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();
    }

    public UserNavInfoDto getUserNavInfo() {
        var user = getCurrentUser();
        return UserNavInfoDto.builder()
                .username(user.getNickname())
                .image(user.getImage())
                .build();
    }

    public String editAccountSettings(String username, AccountSettingsEditDto request) throws IOException {
        var user = getUserByUsername(username);
        if (request.getName() != null)
            user.setName(request.getName());
        if (request.getSurname() != null)
            user.setSurname(request.getSurname());
        if (request.getImage() != null) {
            fileService.deleteImageFromStorage(user.getImage());
            user.setImage(fileService.uploadFile(request.getImage()));
        }
        if (request.getNickname() != null)
            if (userRepository.existsUserByUsername(request.getNickname()))
                throw new SomethingAlreadyExist("Username Already Exist");
            else
                user.setUsername(request.getNickname());

        userRepository.save(user);

        return "Changed";
    }

}
