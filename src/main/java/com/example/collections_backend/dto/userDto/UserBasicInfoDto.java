package com.example.collections_backend.dto.userDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserBasicInfoDto {
    private String name;
    private String surname;
    private String image;
    private String nickname;
}
