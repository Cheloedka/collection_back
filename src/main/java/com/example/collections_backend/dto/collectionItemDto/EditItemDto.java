package com.example.collections_backend.dto.collectionItemDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class EditItemDto {
    private String name;
    private String about;
    private String information;
    private List<String> oldImages = new ArrayList<>();
    private List<MultipartFile> newImages = new ArrayList<>();
    private Long idCollection;
    private Integer countId;
}
