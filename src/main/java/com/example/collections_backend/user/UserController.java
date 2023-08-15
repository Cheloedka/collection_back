package com.example.collections_backend.user;

import com.example.collections_backend.auth.AuthenticationService;
import com.example.collections_backend.dto.userDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/auth/navbar")
    public UserNavInfoDto getUserInfoNavbar() {

        return userService.getUserNavInfo();

    }
    @GetMapping("/user/{username}")
    public UserPageDto getUserPageInfo(@PathVariable(value = "username") String username) {

        return userService.getUserPageInfo(username);
    }

    @GetMapping("/auth/user/{username}/settings")
    public UserSettingsDto getUserSettingsInfo(@PathVariable(value = "username") String username) {

        return userService.getUserSettingsInfo(username);
    }

    @PutMapping(path ="/auth/user/{username}/edit1", consumes = "multipart/form-data")
    public ResponseEntity<String> editAccountSettings(
            @PathVariable String username,
            @ModelAttribute AccountSettingsEditDto request
    ) throws IOException {

        return ResponseEntity.ok(userService.editAccountSettings(username, request));
    }

    @PutMapping("/auth/user/changeEmail")
    public ResponseEntity<String> editEmail(
            @RequestBody SecurityChangeDto request
    ) {

        return ResponseEntity.ok(authenticationService.changeEmail(request.getEmail()));
    }

    @PutMapping("/auth/user/changePassword")
    public ResponseEntity<String> editPassword(
            @RequestBody SecurityChangeDto request
    ) {

        return ResponseEntity.ok(authenticationService.changePassword(request));
    }

}
