package com.example.app.main.service;

import com.example.app.auth.data.User;
import com.example.app.main.data.entity.Dish;
import com.example.app.main.data.repository.DishRepository;
import com.example.app.main.data.repository.OrderRepository;
import com.example.app.main.data.repository.ReviewRepository;
import com.example.api.dto.*;
import com.example.app.main.exception.*;
import com.example.app.main.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserDetailsService userDetailsService;
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final OrderDataModelToOrderDtoMapper orderDataModelToOrderDtoMapper;
    private final OrderDtoToOrderDataModelMapper orderDtoToOrderDataModelMapper;
    private final ReviewDataModelToReviewDtoMapper reviewDataModelToReviewDtoMapper;
    private final ReviewDtoToReviewDataModelMapper reviewDtoToReviewDataModelMapper;
    private final CookingService cookingService;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public Order create(CreateOrderRequest request, Authentication authentication) {
        Long userId = getUserId(authentication);
        List<Dish> dishes = getDishesFromOrder(request.getDishesAmountById());
        com.example.app.main.data.entity.Order order = com.example.app.main.data.entity.Order.builder()
                .status(Status.IN_PROGRESS)
                .userId(userId)
                .isPaid(false)
                .date(request.getDate())
                .dishes(dishes)
                .readyDishesAmount(0)
                .build();
        order = orderRepository.save(order);
        com.example.app.main.data.entity.Order finalOrder = order;
        executor.submit(() -> cookingService.cook(request.getDishesAmountById(), finalOrder.getId()));
        return orderDataModelToOrderDtoMapper.map(order);
    }

    @Override
    public Order view(Long id, Authentication authentication) {
        return isOrderBelongsToUser(id, authentication);
    }

    @Override
    public Order addDishes(Long id, AddToOrderRequest request, Authentication authentication) {
        Order order = isOrderBelongsToUser(id, authentication);
        List<com.example.app.main.data.entity.Dish> dishes = getDishesFromOrder(request.getDishesAmountById());
        if (!order.getStatus().equals(Status.IN_PROGRESS)) {
            throw new OrderAlreadyClosedException(id);
        }
        com.example.app.main.data.entity.Order orderDm = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderDm.getDishes().addAll(dishes);
        orderDm = orderRepository.save(orderDm);
        com.example.app.main.data.entity.Order finalOrder = orderDm;
        executor.submit(() -> cookingService.cook(request.getDishesAmountById(), finalOrder.getId()));
        return orderDataModelToOrderDtoMapper.map(orderDm);
    }

    @Override
    public Order cancel(Long id, Authentication authentication) {
        Order order = isOrderBelongsToUser(id, authentication);
        if (!order.getStatus().equals(Status.IN_PROGRESS)) {
            throw new OrderAlreadyClosedException(id);
        }
        order.setStatus(Status.CANCELLED);
        return orderDataModelToOrderDtoMapper.map(
                orderRepository.save(orderDtoToOrderDataModelMapper.map(order))
        );
    }

    @Override
    public Order pay(Long id, Authentication authentication) {
        Order order = isOrderBelongsToUser(id, authentication);
        if (!order.getStatus().equals(Status.READY)) {
            throw new OrderNotReadyException(id);
        }
        if (order.getIsPaid()) {
            throw new OrderAlreadyPaidException(id);
        }
        order.setIsPaid(true);
        return orderDataModelToOrderDtoMapper.map(
                orderRepository.save(orderDtoToOrderDataModelMapper.map(order))
        );
    }

    @Override
    public Review rate(Long id, AddReviewRequest request, Authentication authentication) {
        Order order = isOrderBelongsToUser(id, authentication);
        if (!order.getStatus().equals(Status.READY)) {
            throw new OrderNotReadyException(id);
        }
        if (!order.getIsPaid()) {
            throw new OrderNotPaidYetException(id);
        }
        if (!order.getDishes().stream().map(com.example.api.dto.Dish::getId).toList().contains(request.getDishId())) {
            throw new DishNotInOrderException(request.getDishId(), id);
        }
        Review review = Review.builder()
                .orderId(id)
                .dishId(request.getDishId())
                .score(request.getScore())
                .comment(request.getComment())
                .build();
        return reviewDataModelToReviewDtoMapper.map(
                reviewRepository.save(reviewDtoToReviewDataModelMapper.map(review))
        );
    }

    private Long getUserId(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return ((User) userDetailsService.loadUserByUsername(username)).getId();
    }

    private List<com.example.app.main.data.entity.Dish> getDishesFromOrder(Map<Long, Integer> dishesAmountById) {
        List<com.example.app.main.data.entity.Dish> dishes = new ArrayList<>();
        for (var pair : dishesAmountById.entrySet()) {
            Long id = pair.getKey();
            com.example.app.main.data.entity.Dish dish = dishRepository.findById(id)
                    .orElseThrow(() -> new DishNotFoundException(id));
            if (!dish.getInMenu()) {
                throw new DishNotInMenuException(id);
            }
            if (dish.getAmount() <= 0) {
                throw new DishIsOutOfStockException(id);
            }
            Integer amount = pair.getValue();
            if (amount <= 0) {
                throw new DishIncorrectAmountException(id, amount);
            }
            dishes.addAll(Collections.nCopies(amount, dish));
            dish.setAmount(dish.getAmount() - amount);
            dishRepository.save(dish);
        }
        return dishes;
    }

    private Order isOrderBelongsToUser(Long id, Authentication authentication) {
        Long userId = getUserId(authentication);
        Order order = orderDataModelToOrderDtoMapper.map(
                orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id))
        );
        if (!order.getUserId().equals(userId)) {
            throw new OtherUserOrderException(id);
        }
        return order;
    }
}
