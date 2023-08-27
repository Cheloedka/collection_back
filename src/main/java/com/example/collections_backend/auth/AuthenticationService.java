package com.example.collections_backend.auth;

import com.example.collections_backend.config.JwtService;
import com.example.collections_backend.dto.userDto.AuthenticationDto;
import com.example.collections_backend.dto.userDto.ConfirmationDto;
import com.example.collections_backend.dto.userDto.RegisterDto;
import com.example.collections_backend.dto.userDto.SecuritySettingsEditDto;
import com.example.collections_backend.email.EmailSenderService;
import com.example.collections_backend.email.token.ConfirmationToken;
import com.example.collections_backend.email.token.ConfirmationTokenRepository;
import com.example.collections_backend.email.token.ConfirmationTokenService;
import com.example.collections_backend.email.token.ConfirmationType;
import com.example.collections_backend.exception_handling.exceptions.ConformationTokenExpiredException;
import com.example.collections_backend.exception_handling.exceptions.PasswordDoesntMatchException;
import com.example.collections_backend.exception_handling.exceptions.UserNotFoundException;
import com.example.collections_backend.response.AuthenticationResponse;
import com.example.collections_backend.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


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
    private final UserManagementService userManagementService;

    //login
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

    private void existsByEmail(String email) {
        if (userRepository.existsUserByEmail(email)) {
            throw new UserNotFoundException("Email is already exist");
        }
    }

    public String register(RegisterDto request) {

        existsByEmail(request.getEmail());

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


        sendVerificationMail(user);

        return "Verification mail is sent on your email";
    }



    public String confirmation(ConfirmationDto request) {

        var token = confirmationTokenRepository
                .findByConfirmationToken(request.getToken())
                .orElseThrow(() -> new ConformationTokenExpiredException("Confirmation token not exist"));

        token.setConfirmedTime(LocalDateTime.now());
        confirmationTokenRepository.save(token);

        var type = token.getConfirmationType();
        String message = "";
        switch (type) {
            case REGISTRATION -> {
                activateAccountIfVerified(token);
                message = "Account is activated";
            }
            case CHANGE_EMAIL -> {
                changeEmailActionConfirmation(token);
                message = "Verification is sent on new email address";
            }
            case CHANGE_EMAIL_LAST -> {
                changeEmailLastConfirmation(token);
                message = "Email changed successfully";
            }
            case PASSWORD_RESET -> {
                resetPasswordAction(token, request.getInfo());
                message = "Password changed successfully";
            }
        }
        return message;
    }

    private void expiredToken(ConfirmationToken token) {
        if (token.getConfirmedTime().isAfter(token.getExpiredTime())) {
            throw new ConformationTokenExpiredException("Confirmation token is expired");
        }
    }

    private void activateAccountIfVerified(ConfirmationToken token) {
        var user = userManagementService.getUserById(token.getUserId());


        if (token.getConfirmedTime().isAfter(token.getExpiredTime())){
            sendVerificationMail(user);
            throw new ConformationTokenExpiredException("Verification token is expired, new one is sent on your e-mail");
        }
        user.setIsEnabled(true);
        userRepository.save(user);
    }

    private void changeEmailActionConfirmation(ConfirmationToken token) { //confirms action "change email"
        expiredToken(token);
        var user = userManagementService.getUserById(token.getUserId());

        String newToken = confirmationTokenService.createConformationToken(user.getIdUser(), ConfirmationType.CHANGE_EMAIL_LAST, token.getMessage());


        emailSenderService.sendNewEmailConfirmationMail(
                token.getMessage(),
                user.getName(),
                newToken
        );
    }

    //?
    private void changeEmailLastConfirmation(ConfirmationToken token) { //new email confirmation
        expiredToken(token);
        var user = userManagementService.getUserById(token.getUserId());

        user.setEmail(token.getMessage());
        userRepository.save(user);
    }


    public void sendVerificationMail( User user ) {

        String token = confirmationTokenService.createConformationToken(user.getIdUser(), ConfirmationType.REGISTRATION);
        emailSenderService.sendVerificationMail(
                user.getUsername(),
                user.getName(),
                token
        );
    }

    public String changeEmail(String email) { //sends email list on old email to confirm action

        existsByEmail(email);
        var user = userManagementService.getCurrentUser();
        String newToken = confirmationTokenService.createConformationToken(
                user.getIdUser(), ConfirmationType.CHANGE_EMAIL, email);
        emailSenderService.sendChangeEmailMail(
                user.getUsername(),
                email,
                user.getName(),
                newToken
        );

        return "Verification is sent on old email address";
    }

    public String resetPassword(String email) {
        var user = userManagementService.getUserByEmail(email);

        String token = confirmationTokenService.createConformationToken(
                user.getIdUser(), ConfirmationType.PASSWORD_RESET);

        emailSenderService.resetPasswordMail(
                email,
                user.getName(),
                token
        );
        return "Verification is sent on email address";
    }

    public void resetPasswordAction(ConfirmationToken token, String message) {
        expiredToken(token);
        var user = userManagementService.getUserById(token.getUserId());

        user.setPassword(passwordEncoder.encode(message));
        userRepository.save(user);
    }


    public String changePassword(SecuritySettingsEditDto dto) {
        var user = userManagementService.getCurrentUser();
        if ( !passwordEncoder.matches(dto.getOldPassword(), user.getPassword()) ) {
            throw new PasswordDoesntMatchException();
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return "Success";
    }

}
