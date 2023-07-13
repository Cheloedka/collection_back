package com.example.collections_backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class CollectionReturnDto {
    private String name;
    private String about;
    private String information;
    private String image;
    private String backgroundImage;
    private boolean isPrivate;
}
