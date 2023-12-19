package com.example.collections_backend.commentary.like;

import com.example.collections_backend.commentary.Commentary;
import com.example.collections_backend.commentary.CommentaryRepository;
import com.example.collections_backend.dto.commentaryDto.CommentaryLikeDto;
import com.example.collections_backend.exception_handling.exceptions.SomethingNotFoundException;
import com.example.collections_backend.notifications.NotificationService;
import com.example.collections_backend.user.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentaryLikeService {

    private final CommentaryRepository commentaryRepository;
    private final CommentaryLikeRepository commentaryLikeRepository;
    private final UserManagementService userManagementService;
    private final NotificationService notificationService;

    public String newLike(Long idCommentary, boolean isDislike) {

        var commentary = commentaryRepository
                .findById(idCommentary)
                .orElseThrow(() -> new SomethingNotFoundException("Commentary is not found"));

        var like = CommentaryLike.builder()
                .commentary(commentary)
                .likeType(isDislike ? LikeType.DISLIKE : LikeType.LIKE)
                .build();

        commentaryLikeRepository.save(like);

        if (userManagementService.getCurrentUser() != commentary.getAuthor()) {
            notificationService.createUpvotesNotification(
                    commentary,
                    countRating(commentary)
            );
        }

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

    public Integer countRating(Commentary commentary) {
        return commentaryLikeRepository.countAllByCommentaryAndLikeType(commentary, LikeType.LIKE)
                -
               commentaryLikeRepository.countAllByCommentaryAndLikeType(commentary, LikeType.DISLIKE);
    }

    public CommentaryLikeDto isExistLike(Commentary commentary) {

        boolean isAuth = userManagementService.isContextUser();

        if (!isAuth) {
            return null;
        }

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
