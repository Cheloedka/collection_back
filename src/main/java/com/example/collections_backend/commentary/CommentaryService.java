package com.example.collections_backend.commentary;

import com.example.collections_backend.collectionItem.CollectionItemRepository;
import com.example.collections_backend.dto.CommentaryDto;
import com.example.collections_backend.dto.UserBasicInfoDto;
import com.example.collections_backend.exception_handling.exceptions.BadRequestException;
import com.example.collections_backend.exception_handling.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentaryService {
    private final CommentaryRepository commentaryRepo;
    private final CollectionItemRepository collectionItemRepo;

    public void newCommentary(CommentaryDto request) {
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
        commentaryRepo.save(commentary.build());
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
                        .build()
                )
                .toList();
    }

}
