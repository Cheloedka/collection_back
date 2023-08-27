package com.example.collections_backend.dto.collectionItemDto;

import com.example.collections_backend.collections.collectionItem.itemImages.ImagesItems;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class NewItemDto {
    private String name;
    private String about;
    private String information;
    private List<ImagesItems> images;
    private Long idCollection;
}
