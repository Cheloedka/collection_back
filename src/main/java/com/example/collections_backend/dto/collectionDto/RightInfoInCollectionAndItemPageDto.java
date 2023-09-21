package com.example.collections_backend.dto.collectionDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RightInfoInCollectionAndItemPageDto {
    private String nameCollection;
    private String aboutCollection;
    private String imageCollection;

    private String firstName;
    private String surname;
    private String userImage;

}
