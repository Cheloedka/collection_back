package com.example.collections_backend.dto.collectionItemDto;


import com.example.collections_backend.collectionItem.itemImages.ImagesItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class GetItemInfoDto {

    private String infoName;
    private String infoImage;

    private String name;
    private String about;
    private String information;

    private List<ImagesItem> images = new ArrayList<>();

    private boolean liked;

    private Long itemId;
    private Integer countId;
    private Long collectionId;

    private Long likesCount;
    private Long commentsCount;

    private boolean isPrivate;
    private LocalDateTime creationTime;
}
