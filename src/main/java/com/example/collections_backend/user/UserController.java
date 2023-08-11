package com.example.collections_backend.user;

import com.example.collections_backend.dto.userDto.AccountSettingsEditDto;
import com.example.collections_backend.dto.userDto.UserNavInfoDto;
import com.example.collections_backend.dto.userDto.UserPageDto;
import com.example.collections_backend.dto.userDto.UserSettingsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/navbar")
    public UserNavInfoDto getUserInfoNavbar(){

        return userService.getUserNavInfo();

    }
    @GetMapping("/user/{username}")
    public UserPageDto getUserPageInfo(@PathVariable(value = "username") String username){

        return userService.getUserPageInfo(username);
    }

    @GetMapping("/auth/user/{username}/settings")
    public UserSettingsDto getUserSettingsInfo(@PathVariable(value = "username") String username){

        return userService.getUserSettingsInfo(username);
    }

    @PutMapping(path ="/auth/user/{username}/edit1", consumes = "multipart/form-data")
    public ResponseEntity<String> editAccountSettings(
            @PathVariable(value = "username") String username,
            @ModelAttribute AccountSettingsEditDto request
            ) throws IOException {

        return ResponseEntity.ok(userService.editAccountSettings(username, request));
    }
}
