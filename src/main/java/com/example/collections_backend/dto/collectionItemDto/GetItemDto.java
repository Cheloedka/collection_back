package com.example.collections_backend.dto.collectionItemDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetItemDto {
    private String itemName;
    private String itemAbout;
    private String itemImage;
}
