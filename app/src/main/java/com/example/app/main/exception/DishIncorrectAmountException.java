package com.example.app.main.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DishIncorrectAmountException extends RuntimeException {
    private final Long id;
    private final Integer amount;
}
