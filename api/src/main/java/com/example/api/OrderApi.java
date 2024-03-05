package com.example.api;

import com.example.api.dto.*;

public interface OrderApi {
    Order create(CreateOrderRequest request);

    Order view(Long id);

    Order addDishes(Long id, AddToOrderRequest request);

    Order cancel(Long id);

    Order pay(Long id);

    Review rate(Long id, AddReviewRequest request);
}
