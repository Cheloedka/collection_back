package com.example.collections_backend.commentary;

import com.example.collections_backend.collectionItem.CollectionItemRepository;
import com.example.collections_backend.commentary.like.CommentaryLikeService;
import com.example.collections_backend.dto.commentaryDto.CommentaryDto;
import com.example.collections_backend.dto.userDto.UserBasicInfoDto;
import com.example.collections_backend.exception_handling.exceptions.BadRequestException;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import com.example.collections_backend.exception_handling.exceptions.SomethingNotFoundException;
import com.example.collections_backend.notifications.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentaryService {
    private final CommentaryRepository commentaryRepo;
    private final CollectionItemRepository collectionItemRepo;
    private final CommentaryLikeService commentaryLikeService;
    private final NotificationService notificationService;

    public void deleteCommentary(Long idCommentary) {
        var commentary = commentaryRepo.findById(idCommentary).orElseThrow(() -> new BadRequestException("Something went wrong"));

        commentaryRepo.delete(commentary);
    }

    public void editCommentary(Long idCommentary, CommentaryDto request) {
        var commentary =
                commentaryRepo.findById(idCommentary).orElseThrow(() -> new BadRequestException("Something went wrong"));
        commentary.setContent(request.getContent());

        commentaryRepo.save(commentary);
    }

    public Long newCommentary(CommentaryDto request) {
        var commentary = Commentary.builder()
                .content(request.getContent())
                .answerToItem(collectionItemRepo.findById(request.getAnswerToItem())
                                .orElseThrow(EntityNotFoundException::new)
                )
                .answerToId(request.getAnswerToId() == null ? null : getCommentaryByIdOrThrowErr(request.getAnswerToId()))
                .build();


        commentaryRepo.save(commentary);

        notificationService.createNewCommentNotification(
                commentary,
                commentary.getAnswerToId() == null
                        ? null
                        : getCommentaryByIdOrThrowErr(commentary.getAnswerToId().getId())
        );

        return commentary.getId();
    }

    private Commentary getCommentaryByIdOrThrowErr(Long id) {
        return commentaryRepo.findById(id)
                .orElseThrow(() -> new SomethingNotFoundException("There is no commentary"));
    }

    public List<CommentaryDto> getAllCommentaryToPost(Long idItem) {

        var item =
                collectionItemRepo
                        .findById(idItem)
                        .orElseThrow(() -> new BadRequestException("Something went wrong"));
        var list =
                commentaryRepo.findAllByAnswerToItemAndAnswerToIdNull(item);

        return commentaryStructureMaker(list);
    }

    private List<CommentaryDto> commentaryStructureMaker(List<Commentary> commentary) {

        if (commentary.size() == 0) {
            return null;
        }

        return commentary.stream()
                .map(c -> CommentaryDto
                        .builder()
                        .id(c.getId())
                        .content(c.getContent())
                        .author(UserBasicInfoDto
                                .builder()
                                .image(c.getAuthor() != null ? c.getAuthor().getImage() : null)
                                .nickname(c.getAuthor() != null ? c.getAuthor().getNickname() : null)
                                .build()
                        )
                        .answers(
                                commentaryStructureMaker(commentaryRepo.findAllByAnswerToId(c))
                        )
                        .countLikes(commentaryLikeService.countRating(c))
                        .likeDto(commentaryLikeService.isExistLike(c))
                        .answerToItem(c.getAnswerToItem().getId())
                        .answerToId(c.getAnswerToId() == null ? null : c.getAnswerToId().getId())
                        .edited(!c.getCreationDate().isEqual(c.getUpdatedDate()))
                        .creationDate(c.getUpdatedDate())
                        .build()
                )
                .toList();
    }

}
