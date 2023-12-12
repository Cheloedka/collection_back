package com.example.collections_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class ChangeBackDto {
    private MultipartFile file;
    private String message;
}
