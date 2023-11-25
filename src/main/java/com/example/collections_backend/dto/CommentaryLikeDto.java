package com.example.collections_backend.dto;

import com.example.collections_backend.commentary.like.LikeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentaryLikeDto {
    private boolean isLiked;
    private LikeType likeType;
}
