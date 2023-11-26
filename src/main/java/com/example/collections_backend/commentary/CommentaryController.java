package com.example.collections_backend.commentary;

import com.example.collections_backend.dto.CommentaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CommentaryController {
    private final CommentaryService commentaryService;

    @PostMapping(value = "auth/commentary/new")
    public Long addCommentary(@RequestBody CommentaryDto request) {
        return commentaryService.newCommentary(request);
    }

    @PutMapping(value = "auth/commentary/edit/{idCommentary}")
    public ResponseEntity<String> editCommentary(@PathVariable Long idCommentary, @RequestBody CommentaryDto request) {
        commentaryService.editCommentary(idCommentary, request);
        return ResponseEntity.ok("Success");
    }

    @GetMapping(value = "auth/commentary/{idItem}")
    public ResponseEntity<List<CommentaryDto>> addCommentary(@PathVariable Long idItem) {
        return ResponseEntity.ok(commentaryService.getAllCommentaryToPost(idItem));
    }

    @DeleteMapping(value = "auth/commentary/delete/{idCommentary}")
    public ResponseEntity<String> deleteCommentary(@PathVariable Long idCommentary) {
        commentaryService.deleteCommentary(idCommentary);
        return ResponseEntity.ok("Success");
    }
}
