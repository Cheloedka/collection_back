package com.example.collections_backend.collectionItem.itemLikes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/auth/like/{id}")
    public ResponseEntity<String> Like(@PathVariable Long id) {

        return ResponseEntity.ok(likeService.newLike(id));
    }

    @DeleteMapping("/auth/like/delete/{id}")
    public ResponseEntity<String> DeleteLike(@PathVariable Long id) {

        return ResponseEntity.ok(likeService.deleteLike(id));
    }
}
