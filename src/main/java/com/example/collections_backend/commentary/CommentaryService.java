package com.example.collections_backend.commentary;

import com.example.collections_backend.collectionItem.CollectionItemRepository;
import com.example.collections_backend.commentary.like.CommentaryLikeService;
import com.example.collections_backend.dto.CommentaryDto;
import com.example.collections_backend.dto.UserBasicInfoDto;
import com.example.collections_backend.exception_handling.exceptions.BadRequestException;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentaryService {
    private final CommentaryRepository commentaryRepo;
    private final CollectionItemRepository collectionItemRepo;
    private final CommentaryLikeService commentaryLikeService;

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
                .answerToItem(
                        collectionItemRepo
                                .findById(request.getAnswerToItem())
                                .orElseThrow(EntityNotFoundException::new)
                );

        if(request.getAnswerToId() != null) {
            commentary.answerToId(
                    commentaryRepo
                            .findById(request.getAnswerToId())
                            .orElseThrow(() -> new BadRequestException("Something went wrong"))
            );
        }

        return commentaryRepo.save(commentary.build()).getId();
    }

    public List<CommentaryDto> getAllCommentaryToPost(Long idItem) {
        var item = collectionItemRepo.findById(idItem).orElseThrow(() -> new BadRequestException("Something went wrong"));
        List<Commentary> commentary = commentaryRepo.findAllByAnswerToItemAndAnswerToIdNull(item);

        return commentaryStructureMaker(commentary);
    }

    private List<CommentaryDto> commentaryStructureMaker(List<Commentary> commentary) {

        if(commentary.size() == 0) {
            return null;
        }

        return commentary.stream()
                .map(c -> CommentaryDto
                        .builder()
                        .id(c.getId())
                        .creationDate(c.getCreationDate())
                        .content(c.getContent())
                        .author(UserBasicInfoDto
                                .builder()
                                .image(c.getAuthor().getImage())
                                .nickname(c.getAuthor().getNickname())
                                .build()
                        )
                        .answers(
                                commentaryStructureMaker(commentaryRepo.findAllByAnswerToId(c))
                        )
                        .countLikes(commentaryLikeService.countLikes(c))
                        .likeDto(commentaryLikeService.isExistLike(c))
                        .answerToId(c.getAnswerToItem().getId())
                        .build()
                )
                .toList();
    }

}
