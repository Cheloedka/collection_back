package com.example.collections_backend.friendship;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/auth/friendships/{username}")
    public ResponseEntity<String> Following(@PathVariable(value = "username") String username) {

        return ResponseEntity.ok(friendshipService.newFollowing(username));
    }

    @DeleteMapping("/auth/friendships/delete/{username}")
    public ResponseEntity<String> DeleteFollowing(@PathVariable(value = "username") String username) {

        return ResponseEntity.ok(friendshipService.deleteFollowing(username));
    }

}
