package com.example.collections_backend.commentary.like;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class CommentaryLikeController {

    private final CommentaryLikeService commentaryLikeService;

    @PostMapping("/auth/commentary/like/{id}")
    public ResponseEntity<String> Like(@PathVariable Long id) {

        return ResponseEntity.ok(commentaryLikeService.newLike(id, false));
    }

    @PostMapping("/auth/commentary/dislike/{id}")
    public ResponseEntity<String> Dislike(@PathVariable Long id) {

        return ResponseEntity.ok(commentaryLikeService.newLike(id, true));
    }

    @DeleteMapping("/auth/commentary/like/delete/{id}")
    public ResponseEntity<String> DeleteLike(@PathVariable Long id) {

        return ResponseEntity.ok(commentaryLikeService.deleteLike(id));
    }
}
