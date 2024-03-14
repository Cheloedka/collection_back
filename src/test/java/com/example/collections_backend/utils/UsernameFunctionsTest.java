package com.example.collections_backend.utils;

import com.example.collections_backend.exception_handling.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UsernameFunctionsTest {
    @Test
    void checkUsername_withWrongUsername() {
        Assertions.assertThrows(UserNotFoundException.class, () -> UsernameFunctions.checkUsername("user"));
    }

    @Test
    void checkUsername_withValidUsername() {
        Assertions.assertDoesNotThrow(() -> UsernameFunctions.checkUsername("User12345"));
    }
}
