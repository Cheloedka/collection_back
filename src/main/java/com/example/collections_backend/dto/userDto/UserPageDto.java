package com.example.collections_backend.dto.userDto;

import com.example.collections_backend.dto.CollectionBasicInfo;
import com.example.collections_backend.dto.UserBasicInfoDto;
import com.example.collections_backend.friendship.Friendship;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
public class UserPageDto {
    private String username;
    private String name;
    private String surname;
    private String image;
    private String backgroundImage;
    private Long countCollections;
    private List<CollectionBasicInfo> collections;
    private Long countFriendships;
    private List<UserBasicInfoDto> friendships;
    private boolean isFollower;
}
