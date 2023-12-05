package com.example.collections_backend.friendship;

import com.example.collections_backend.dto.UserBasicInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/auth/friendships/{username}")
    public ResponseEntity<String> following(@PathVariable String username) {

        return ResponseEntity.ok(friendshipService.newFollowing(username));
    }

    @GetMapping("/following/{username}")
    public List<UserBasicInfoDto> getFollowing(@PathVariable String username) {
        return friendshipService.getFollowing(username);
    }

    @GetMapping("/followers/{username}")
    public List<UserBasicInfoDto> getFollowers(@PathVariable String username) {
        return friendshipService.getFollowers(username);
    }

    @DeleteMapping("/auth/friendships/delete/{username}")
    public ResponseEntity<String> deleteFollowing(@PathVariable String username) {

        return ResponseEntity.ok(friendshipService.deleteFollowing(username));
    }

}
