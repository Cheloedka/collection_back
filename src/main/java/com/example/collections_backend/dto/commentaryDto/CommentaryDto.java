package com.example.collections_backend.dto.commentaryDto;

import com.example.collections_backend.dto.userDto.UserBasicInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CommentaryDto {
    private Long id;
    private String content;

    private LocalDateTime creationDate;

    private UserBasicInfoDto author;
    private Long answerToItem;
    private Long answerToId;
    private List<CommentaryDto> answers;
    private Integer countLikes;
    private CommentaryLikeDto likeDto;
}

