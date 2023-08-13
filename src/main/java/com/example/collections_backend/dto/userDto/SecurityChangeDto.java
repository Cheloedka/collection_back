package com.example.collections_backend.dto.userDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityChangeDto {
    private String email;
    private String newPassword;
    private String oldPassword;
}
