package com.example.api;

import com.example.api.dto.*;

import java.util.List;

public interface StatisticsApi {
    List<Order> showOrders();

    List<Order> showOrdersByDate(DateOrderRequest request);

    Integer showTotal();

    List<Review> showReviews();

    List<MeanRatingResponse> showMeanRatings();

    List<DishOrderAmountResponse> showDishOrderAmounts();
}
