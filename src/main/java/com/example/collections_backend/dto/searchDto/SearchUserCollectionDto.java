package com.example.collections_backend.dto.searchDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class SearchUserCollectionDto {
    private List<SearchItemDto> users;
    private Long usersLength;
    private List<SearchItemDto> collections;
    private Long collectionsLength;
}
