package com.example.collections_backend.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class CollectionAddDto {
    private String name;
    private String about;
    private String information;
    private MultipartFile image;
    private MultipartFile backgroundImage;
    private boolean isPrivate;
}
