package com.example.collections_backend.dto.userDto;

import com.example.collections_backend.collections.Collection;
import com.example.collections_backend.friendship.Friendship;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSettingsDto {
    private String name;
    private String surname;
    private String email;
}
