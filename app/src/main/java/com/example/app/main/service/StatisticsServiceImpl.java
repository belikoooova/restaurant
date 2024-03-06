package com.example.app.main.service;

import com.example.app.main.data.repository.DishRepository;
import com.example.app.main.data.repository.OrderRepository;
import com.example.app.main.data.repository.ReviewRepository;
import com.example.api.dto.*;
import com.example.app.main.exception.DateWrongOrderException;
import com.example.app.main.exception.DishNotFoundException;
import com.example.app.main.mapper.OrderDataModelToOrderDtoMapper;
import com.example.app.main.mapper.ReviewDataModelToReviewDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private static final int ID = 0;
    private static final int SCORE = 1;
    private static final int ORDERS = 1;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final DishRepository dishRepository;
    private final OrderDataModelToOrderDtoMapper orderDataModelToOrderDtoMapper;
    private final ReviewDataModelToReviewDtoMapper reviewDataModelToReviewDtoMapper;

    @Override
    public List<Order> showOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderDataModelToOrderDtoMapper::map)
                .toList();
    }

    @Override
    public List<Order> showOrdersByDate(DateOrderRequest request) {
        if (request.getFrom().isAfter(request.getTo())) {
            throw new DateWrongOrderException();
        }
        return orderRepository.findAllByDateGreaterThanEqualAndDateLessThanEqual(request.getFrom(), request.getTo())
                .stream()
                .map(orderDataModelToOrderDtoMapper::map)
                .toList();
    }

    @Override
    public Integer showTotal() {
        return orderRepository.getTotalSum();
    }

    @Override
    public List<Review> showReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(reviewDataModelToReviewDtoMapper::map)
                .toList();
    }

    @Override
    public List<MeanRatingResponse> showMeanRatings() {
        return reviewRepository.findAverageScoreByDish()
                .stream()
                .map(this::mapObjectArrayToMeanRatingResponse)
                .toList();
    }

    @Override
    public List<DishOrderAmountResponse> showDishOrderAmounts() {
        return reviewRepository.findDishesOrderedCountDesc()
                .stream()
                .map(this::mapObjectArrayToDishOrderAmountResponse)
                .sorted(Comparator.comparing(DishOrderAmountResponse::getOrders).reversed())
                .toList();
    }

    private MeanRatingResponse mapObjectArrayToMeanRatingResponse(Object[] objects) {
        Long id = (Long) objects[ID];
        String title = dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(id))
                .getTitle();
        return MeanRatingResponse.builder()
                .id(id)
                .title(title)
                .score(((BigDecimal) objects[SCORE]).doubleValue())
                .build();
    }

    private DishOrderAmountResponse mapObjectArrayToDishOrderAmountResponse(Object[] objects) {
        Long id = (Long) objects[ID];
        String title = dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(id))
                .getTitle();
        return DishOrderAmountResponse.builder()
                .id(id)
                .title(title)
                .orders((Long) objects[ORDERS])
                .build();
    }
}
