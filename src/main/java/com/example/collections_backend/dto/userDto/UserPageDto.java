package com.example.collections_backend.dto.userDto;

import com.example.collections_backend.collections.CollectionEntity;
import com.example.collections_backend.friendship.Friendship;
import lombok.*;


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
    private Iterable<CollectionEntity> collections;
    private Long countFriendships;
    private Iterable<Friendship> friendships;
    private boolean isFollower;
}
