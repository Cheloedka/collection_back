package com.example.collections_backend.dto.collectionDto;

import com.example.collections_backend.dto.collectionItemDto.GetShortItemInfoDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private List<GetShortItemInfoDto> items = new ArrayList<>();
    private Long countItems;
}
