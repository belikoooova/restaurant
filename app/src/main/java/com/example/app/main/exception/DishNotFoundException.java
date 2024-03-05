package com.example.app.main.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DishNotFoundException extends RuntimeException {
    private final Long id;
}
