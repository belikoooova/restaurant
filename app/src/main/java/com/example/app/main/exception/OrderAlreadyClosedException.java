package com.example.app.main.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderAlreadyClosedException extends RuntimeException {
    private final Long id;
}
