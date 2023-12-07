package com.example.collections_backend.dto.commentaryDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CommentaryPageDto {
    private List<CommentaryDto> firstCommentary;
    private Long countCommentary;
}
