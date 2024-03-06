package com.example.app.main.controller;

import com.example.api.StatisticsApi;
import com.example.api.dto.DateOrderRequest;
import com.example.api.dto.DishOrderAmountResponse;
import com.example.api.dto.MeanRatingResponse;
import com.example.api.dto.Review;
import com.example.api.dto.Order;
import com.example.app.main.service.StatisticsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController implements StatisticsApi {
    private final StatisticsService statisticsService;

    @Override
    @GetMapping("/orders")
    @Secured("ROLE_ADMIN")
    public List<Order> showOrders() {
        return statisticsService.showOrders();
    }

    @Override
    @GetMapping("/orders-by-period")
    @Secured("ROLE_ADMIN")
    public List<Order> showOrdersByDate(@RequestBody @Valid DateOrderRequest request) {
        return statisticsService.showOrdersByDate(request);
    }

    @Override
    @GetMapping("/total")
    @Secured("ROLE_ADMIN")
    public Integer showTotal() {
        return statisticsService.showTotal();
    }

    @Override
    @GetMapping("/reviews")
    @Secured("ROLE_ADMIN")
    public List<Review> showReviews() {
        return statisticsService.showReviews();
    }

    @Override
    @GetMapping("/average-ratings")
    @Secured("ROLE_ADMIN")
    public List<MeanRatingResponse> showMeanRatings() {
        return statisticsService.showMeanRatings();
    }

    @Override
    @GetMapping("/dish-order-amounts")
    @Secured("ROLE_ADMIN")
    public List<DishOrderAmountResponse> showDishOrderAmounts() {
        return statisticsService.showDishOrderAmounts();
    }
}
