package com.example.collections_backend.auth;

import com.example.collections_backend.dto.userDto.AuthenticationDto;
import com.example.collections_backend.dto.userDto.ConfirmationDto;
import com.example.collections_backend.dto.userDto.RegisterDto;
import com.example.collections_backend.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/user/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto request) {

        return ResponseEntity.ok(authenticationService.register(request));

    }

    @PostMapping("/user/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationDto request) {

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/user/confirmation")
    public ResponseEntity<String> confirm(@RequestBody ConfirmationDto request) {

        return ResponseEntity.ok(authenticationService.confirmation(request));
    }

}
