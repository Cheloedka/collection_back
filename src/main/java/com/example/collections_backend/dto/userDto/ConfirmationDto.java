package com.example.collections_backend.dto.userDto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ConfirmationDto {
    private String token;
    private LocalDateTime confirmedTime = LocalDateTime.now();
}
