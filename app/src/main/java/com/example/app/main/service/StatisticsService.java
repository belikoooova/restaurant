package com.example.app.main.service;

import com.example.api.dto.*;

import java.util.List;

public interface StatisticsService {
    List<Order> showOrders();

    List<Order> showOrdersByDate(DateOrderRequest request);

    Integer showTotal();

    List<Review> showReviews();

    List<MeanRatingResponse> showMeanRatings();

    List<DishOrderAmountResponse> showDishOrderAmounts();
}
