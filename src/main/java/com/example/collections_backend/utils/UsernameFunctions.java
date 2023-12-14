package com.example.collections_backend.utils;

import com.example.collections_backend.exception_handling.exceptions.UserNotFoundException;

import java.util.Arrays;

public class UsernameFunctions {
    private static final String[] blockedWords = {
            "user",
            "settings",
            "collections",
            "items",
            "following",
            "logout",
            "error",
            "not_found",
            "info",
            "confirmation",
            "resetMail",
            "newMail",
            "resetPwd",
            "login",
            "registration",
            "resetPassword",
            "main"
    };

    public static void checkUsername(String username) {
        Arrays.stream(blockedWords).forEach(w -> {
            if (w.equalsIgnoreCase(username)) {
                throw new UserNotFoundException("Try another username");
            }
        });
    }
}
