package com.example.collections_backend.dto.userDto;

import lombok.*;

@Getter
@Setter
@Builder
public class RegisterDto {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
}
