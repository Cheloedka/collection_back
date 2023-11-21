package com.example.collections_backend.commentary.like;


import com.example.collections_backend.collectionItem.itemLikes.LikeItem;
import com.example.collections_backend.commentary.CommentaryRepository;
import com.example.collections_backend.exception_handling.exceptions.BadRequestException;
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

    public String newLike(Long idCommentary) {
        var like = CommentaryLike.builder()
                .commentary(commentaryRepository
                        .findById(idCommentary)
                        .orElseThrow(() -> new SomethingNotFoundException("Commentary is not found"))
                )
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
}
