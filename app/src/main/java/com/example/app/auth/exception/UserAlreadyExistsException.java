package com.example.app.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private final String username;
}
