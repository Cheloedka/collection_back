package com.example.collections_backend.commentary;

import com.example.collections_backend.dto.commentaryDto.CommentaryDto;
import com.example.collections_backend.dto.commentaryDto.CommentaryPageDto;
import com.example.collections_backend.dto.searchDto.SearchItemDto;
import com.example.collections_backend.dto.searchDto.SearchUserCollectionDto;
import com.example.collections_backend.exception_handling.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentaryController {

    private static final String DEFAULT_PAGE_SIZE = "5";

    private final CommentaryService commentaryService;

    @PostMapping(value = "/auth/commentary/new")
    public ResponseEntity<Long> addCommentary(@RequestBody CommentaryDto request) {

        return ResponseEntity.ok(commentaryService.newCommentary(request));
    }

    @PutMapping(value = "/auth/commentary/edit/{idCommentary}")
    public ResponseEntity<String> editCommentary(@PathVariable Long idCommentary, @RequestBody CommentaryDto request) {
        commentaryService.editCommentary(idCommentary, request);
        return ResponseEntity.ok("Success");
    }


    @GetMapping("/commentary/{idItem}")
    public List<CommentaryDto> getCommentaryToPost(@PathVariable Long idItem) {

        return commentaryService.getAllCommentaryToPost(idItem);
    }

    @DeleteMapping(value = "/auth/commentary/delete/{idCommentary}")
    public ResponseEntity<String> deleteCommentary(@PathVariable Long idCommentary) {
        commentaryService.deleteCommentary(idCommentary);
        return ResponseEntity.ok("Success");
    }
}
