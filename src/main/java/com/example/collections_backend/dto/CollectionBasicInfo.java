package com.example.collections_backend.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CollectionBasicInfo {
    private String name;
    private String about;
    private String image;
    private String info;
    private Long idCollection;
}
