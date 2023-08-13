package com.example.collections_backend.auth;

import com.example.collections_backend.config.JwtService;
import com.example.collections_backend.dto.userDto.AuthenticationDto;
import com.example.collections_backend.dto.userDto.ConfirmationDto;
import com.example.collections_backend.dto.userDto.RegisterDto;
import com.example.collections_backend.dto.userDto.SecurityChangeDto;
import com.example.collections_backend.email.EmailSenderService;
import com.example.collections_backend.email.token.ConfirmationTokenRepository;
import com.example.collections_backend.email.token.ConfirmationTokenService;
import com.example.collections_backend.exception_handling.exceptions.ConformationTokenExpiredException;
import com.example.collections_backend.exception_handling.exceptions.PasswordDoesntMatchException;
import com.example.collections_backend.exception_handling.exceptions.UserNotFoundException;
import com.example.collections_backend.response.AuthenticationResponse;
import com.example.collections_backend.user.Role;
import com.example.collections_backend.user.User;
import com.example.collections_backend.user.UserRepository;
import com.example.collections_backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserService userService;

    public String register(RegisterDto request) {

        existByEmail(request.getEmail());

        var user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .isEnabled(false)
                .build();
        userRepository.save(user);

        String token = confirmationTokenService.createConformationToken(user);

        emailSenderService.sendMail(
                request.getEmail(),
                request.getUsername(),
                token
        );

        return "Confirm token";
    }

    public AuthenticationResponse authenticate(AuthenticationDto request) {
        //check without my code is authorized
        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Email not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ) //throws BadCredentialsException
        );

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public String confirmation(ConfirmationDto request) {
        var token = confirmationTokenRepository
                .findByConfirmationToken(request.getToken())
                .orElseThrow(() -> new ConformationTokenExpiredException("Confirmation token not exist"));


        if (request.getConfirmedTime().isAfter(token.getExpiredTime())) {
            throw new ConformationTokenExpiredException("Confirmation token is expired");
        }

        userService.confirmAccount(token.getUser().getIdUser());
        return "Confirmed";
    }
    public void existByEmail(String email) {
        if (userRepository.existsUserByEmail(email)) {
            throw new UserNotFoundException("User already exist");
        }
    }

    public String changePassword(SecurityChangeDto dto) {
        var user = userService.getCurrentUser();
        if ( !passwordEncoder.matches(dto.getOldPassword(), user.getPassword()) ) {
            throw new PasswordDoesntMatchException();
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return "Success";
    }

}
