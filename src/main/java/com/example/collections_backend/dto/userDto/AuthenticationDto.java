package com.example.collections_backend.dto.userDto;

import lombok.*;

@Setter
@Getter
public class AuthenticationDto {
    private String email;
    private String password;
}
