package com.example.collections_backend.dto.collectionDto;

import lombok.*;

@Getter
@Setter
@Builder
public class ReturnCollectionDto {
    private String name;
    private String about;
    private String information;
    private String image;
    private String backgroundImage;
    private boolean isPrivate;
    private String userFirstName;
    private String userSurname;
    private String userImage;
}
