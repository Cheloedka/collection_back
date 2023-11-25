package com.example.collections_backend.commentary.like;

import com.example.collections_backend.commentary.Commentary;
import com.example.collections_backend.commentary.CommentaryRepository;
import com.example.collections_backend.dto.CommentaryLikeDto;
import com.example.collections_backend.exception_handling.exceptions.SomethingNotFoundException;
import com.example.collections_backend.user.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentaryLikeService {

    private final CommentaryRepository commentaryRepository;
    private final CommentaryLikeRepository commentaryLikeRepository;
    private final UserManagementService userManagementService;

    public String newLike(Long idCommentary, boolean isDislike) {
        var like = CommentaryLike.builder()
                .commentary(commentaryRepository
                        .findById(idCommentary)
                        .orElseThrow(() -> new SomethingNotFoundException("Commentary is not found"))
                )
                .likeType(isDislike ? LikeType.DISLIKE : LikeType.LIKE)
                .build();

        commentaryLikeRepository.save(like);
        return "Success";
    }

    public String deleteLike(Long id) {

        var like = commentaryLikeRepository.findByCommentaryAndIdUser(
                        commentaryRepository.findById(id)
                                .orElseThrow(() -> new SomethingNotFoundException("Commentary is not found")),
                        userManagementService.getCurrentUser()
                )
                .orElseThrow(() -> new SomethingNotFoundException("Like not found"));

        commentaryLikeRepository.delete(like);
        return "Deleted";
    }

    public Integer countLikes(Commentary commentary) {
        return commentaryLikeRepository.countAllByCommentaryAndLikeType(commentary, LikeType.LIKE)
                -
               commentaryLikeRepository.countAllByCommentaryAndLikeType(commentary, LikeType.DISLIKE);
    }

    public CommentaryLikeDto isExistLike(Commentary commentary) {

        var like = commentaryLikeRepository
                .findByCommentaryAndIdUser(commentary, userManagementService.getCurrentUser());

        var dto = CommentaryLikeDto.builder();

        like.ifPresentOrElse(commentaryLike -> dto
                .isLiked(true)
                .likeType(commentaryLike.getLikeType()),
                () -> dto.isLiked(false));

        return dto.build();

    }
}
