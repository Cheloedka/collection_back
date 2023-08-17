package com.example.collections_backend.dto.userDto;

import lombok.*;

@Getter
@Setter
@Builder
public class ConfirmationDto {
    private String token;
    private String info;
}
