package com.example.collections_backend.dto.searchDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchItemDto {
    private String idName;
    private String title;
    private String image;
}
