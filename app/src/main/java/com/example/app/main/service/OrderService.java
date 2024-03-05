package com.example.app.main.service;

import com.example.api.dto.*;
import org.springframework.security.core.Authentication;

public interface OrderService {
    Order create(CreateOrderRequest request, Authentication authentication);

    Order view(Long id, Authentication authentication);

    Order addDishes(Long id, AddToOrderRequest request, Authentication authentication);

    Order cancel(Long id, Authentication authentication);

    Order pay(Long id, Authentication authentication);

    Review rate(Long id, AddReviewRequest request, Authentication authentication);
}
