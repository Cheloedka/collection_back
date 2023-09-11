package com.example.collections_backend.dto.collectionItemDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetShortItemInfoDto {
    private String itemName;
    private String itemAbout;
    private String itemImage;
    private Long itemId;
    private boolean liked;
    private Integer countId;
}
