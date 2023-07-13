package com.example.collections_backend.user;

import com.example.collections_backend.dto.userDto.UserNavInfoDto;
import com.example.collections_backend.dto.userDto.UserPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



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
}
