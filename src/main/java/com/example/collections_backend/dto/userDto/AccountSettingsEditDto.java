package com.example.collections_backend.dto.userDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@ToString
public class AccountSettingsEditDto {
    private String name;
    private String surname;
    private MultipartFile image;
    private String nickname;
}
