package com.example.collections_backend.dto.collectionItemDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Getter
@Setter
@Builder
public class NewItemDto {
    private String name;
    private String about;
    private String information;
    private ArrayList<MultipartFile> images = new ArrayList<>();
    private Long idCollection;
}
