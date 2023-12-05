package com.example.collections_backend.auth;

import com.example.collections_backend.dto.userDto.AuthenticationDto;
import com.example.collections_backend.dto.userDto.ConfirmationDto;
import com.example.collections_backend.dto.userDto.RegisterDto;
import com.example.collections_backend.dto.userDto.SecuritySettingsEditDto;
import com.example.collections_backend.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @PostMapping("/user/refreshToken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/user/confirmation")
    public ResponseEntity<String> confirm(@RequestBody ConfirmationDto request) {

        return ResponseEntity.ok(authenticationService.confirmation(request));
    }

    @PutMapping("/user/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody SecuritySettingsEditDto request) {

        return ResponseEntity.ok(authenticationService.resetPassword(request.getEmail()));
    }
}
