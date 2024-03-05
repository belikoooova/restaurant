package com.example.app.main.handler;

import com.example.app.main.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    private static final String DISH_NOT_FOUND = "Dish with id %d not found";
    private static final String DISH_NOT_IN_MENU = "Dish with id %d not in menu";
    private static final String DISH_OUT_OF_STOCK = "Dish with id %d out of stock";
    private static final String DISH_INCORRECT_AMOUNT = "Dish with id %d has non-positive amount %d";
    private static final String ORDER_NOT_FOUND = "Order with id %d not found";
    private static final String ORDER_OTHER_USER = "Order with id %d belongs to other user";
    private static final String ORDER_ALREADY_CLOSED = "Order with id %d already closed";
    private static final String ORDER_NOT_READY = "Order with id %d not ready yet or already closed";
    private static final String ORDER_ALREADY_PAID = "Order with id %d already paid";
    private static final String COOKING_ERROR = "Error while cooking";
    private static final String ORDER_NOT_PAID = "Order with id %d not paid yet";
    private static final String DISH_NOT_IN_ORDER = "Dish with id %d not in order with id %d";
    private static final String DATE_ERROR = "Start date must be less or equal than end date";

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<String> handleDishNotFoundException(DishNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DISH_NOT_FOUND.formatted(e.getId()));
    }

    @ExceptionHandler(DishNotInMenuException.class)
    public ResponseEntity<String> handleDishNotInMenuException(DishNotInMenuException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DISH_NOT_IN_MENU.formatted(e.getId()));
    }

    @ExceptionHandler(DishIsOutOfStockException.class)
    public ResponseEntity<String> handleDishIsOutOfStockException(DishIsOutOfStockException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DISH_OUT_OF_STOCK.formatted(e.getId()));
    }

    @ExceptionHandler(DishIncorrectAmountException.class)
    public ResponseEntity<String> handleDishIncorrectAmountException(DishIncorrectAmountException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(DISH_INCORRECT_AMOUNT.formatted(e.getId(), e.getAmount()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ORDER_NOT_FOUND.formatted(e.getId()));
    }

    @ExceptionHandler(OtherUserOrderException.class)
    public ResponseEntity<String> handleOtherUserOrderException(OtherUserOrderException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ORDER_OTHER_USER.formatted(e.getId()));
    }

    @ExceptionHandler(OrderAlreadyClosedException.class)
    public ResponseEntity<String> handleOrderAlreadyClosedException(OrderAlreadyClosedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ORDER_ALREADY_CLOSED.formatted(e.getId()));
    }

    @ExceptionHandler(OrderNotReadyException.class)
    public ResponseEntity<String> handleOrderIsNotReadyException(OrderNotReadyException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ORDER_NOT_READY.formatted(e.getId()));
    }

    @ExceptionHandler(OrderAlreadyPaidException.class)
    public ResponseEntity<String> handleOrderAlreadyPayedException(OrderAlreadyPaidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ORDER_ALREADY_PAID.formatted(e.getId()));
    }

    @ExceptionHandler(CookingMultiThreadException.class)
    public ResponseEntity<String> handleCookingMultiThreadException(CookingMultiThreadException ignored) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(COOKING_ERROR);
    }

    @ExceptionHandler(OrderNotPaidYetException.class)
    public ResponseEntity<String> handleOrderNotPaidYetException(OrderNotPaidYetException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ORDER_NOT_PAID.formatted(e.getId()));
    }

    @ExceptionHandler(DishNotInOrderException.class)
    public ResponseEntity<String> handleDishNotInOrderException(DishNotInOrderException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(DISH_NOT_IN_ORDER.formatted(e.getDishId(), e.getOrderId()));
    }

    @ExceptionHandler(DateWrongOrderException.class)
    public ResponseEntity<String> handleDateWrongOrderException(DateWrongOrderException ignored) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DATE_ERROR);
    }
}
