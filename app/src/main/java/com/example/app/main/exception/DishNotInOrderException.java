package com.example.app.main.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DishNotInOrderException extends RuntimeException {
    private final Long dishId;
    private final Long orderId;
}
