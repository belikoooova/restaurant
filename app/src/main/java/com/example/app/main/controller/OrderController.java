package com.example.app.main.controller;

import com.example.api.OrderApi;
import com.example.api.dto.AddReviewRequest;
import com.example.api.dto.AddToOrderRequest;
import com.example.api.dto.CreateOrderRequest;
import com.example.api.dto.Review;
import com.example.api.dto.Order;
import com.example.app.main.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController implements OrderApi {
    private final OrderService orderService;

    @Override
    @PostMapping
    @Secured("ROLE_USER")
    public Order create(@RequestBody @Valid CreateOrderRequest request) {
        return orderService.create(request, SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    public Order view(@PathVariable Long id) {
        return orderService.view(id, SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    @PostMapping("/{id}/dishes")
    @Secured("ROLE_USER")
    public Order addDishes(@PathVariable Long id, @RequestBody @Valid AddToOrderRequest request) {
        return orderService.addDishes(id, request, SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    public Order cancel(@PathVariable Long id) {
        return orderService.cancel(id, SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    @PostMapping("/{id}/pay")
    @Secured("ROLE_USER")
    public Order pay(@PathVariable Long id) {
        return orderService.pay(id, SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    @PostMapping("/{id}/rate")
    @Secured("ROLE_USER")
    public Review rate(@PathVariable Long id, @RequestBody @Valid AddReviewRequest request) {
        return orderService.rate(id, request, SecurityContextHolder.getContext().getAuthentication());
    }
}
