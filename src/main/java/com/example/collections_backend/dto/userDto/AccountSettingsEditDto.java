package com.example.collections_backend.dto.userDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class AccountSettingsEditDto {
    private String name;
    private String surname;
    private MultipartFile image;
}
