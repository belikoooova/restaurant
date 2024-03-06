package com.example.app.main.service;

import com.example.app.main.data.entity.Order;
import com.example.app.main.data.repository.DishRepository;
import com.example.app.main.data.repository.OrderRepository;
import com.example.api.dto.Status;
import com.example.app.main.exception.CookingMultiThreadException;
import com.example.app.main.exception.DishNotFoundException;
import com.example.app.main.exception.OrderNotFoundException;
import com.example.app.main.mapper.OrderDataModelToOrderDtoMapper;
import com.example.app.main.mapper.OrderDtoToOrderDataModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CookingServiceImpl implements CookingService {
    private static final int MILLISECONDS = 1000;
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final OrderDataModelToOrderDtoMapper orderDataModelToOrderDtoMapper;
    private final OrderDtoToOrderDataModelMapper orderDtoToOrderDataModelMapper;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void cook(Map<Long, Integer> dishesAmountById, Long orderId) {
        List<Integer> cookingTimes = getCookingTimes(dishesAmountById);
        try {
            multiThreadCook(cookingTimes, orderId);
        } catch (InterruptedException e) {
            throw new CookingMultiThreadException(e);
        }
        Order order = updateCookedAmount(cookingTimes, orderId);
        if (order.getStatus().equals(Status.CANCELLED)) {
            log.info("The order with ID %d was canceled.".formatted(orderId));
            return;
        }
        if (order.getDishes().size() == order.getReadyDishesAmount()) {
            setReady(order);
            log.info("The order with ID %d is ready.".formatted(orderId));
            return;
        }
        log.info("The portion of the order with ID %d was prepared, but the user added new dishes to the order."
                .formatted(orderId));
    }

    public synchronized Order updateCookedAmount(List<Integer> cookingTimes, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        order.setReadyDishesAmount(order.getReadyDishesAmount() + cookingTimes.size());
        return orderRepository.save(
                orderDtoToOrderDataModelMapper.map(orderDataModelToOrderDtoMapper.map(order))
        );
    }

    public synchronized void setReady(Order order) {
        order.setStatus(Status.READY);
        orderRepository.save(
                orderDtoToOrderDataModelMapper.map(orderDataModelToOrderDtoMapper.map(order))
        );
    }

    private void multiThreadCook(List<Integer> cookingTimes, Long orderId) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(cookingTimes.size());
        log.info("The order with ID %d starts or continue to be prepared.".formatted(orderId));
        for (Integer time : cookingTimes) {
            executor.submit(() -> {
                try {
                    Thread.sleep((long) time * MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new CookingMultiThreadException(e);
                } finally {
                    log.info("The dish with cooking time %d is ready.".formatted(time));
                    latch.countDown();
                }
            });
        }
        latch.await();

    }

    private List<Integer> getCookingTimes(Map<Long, Integer> dishesAmountById) {
        List<Integer> cookingTimes = new ArrayList<>();
        for (var pair : dishesAmountById.entrySet()) {
            Long id = pair.getKey();
            com.example.app.main.data.entity.Dish dish = dishRepository.findById(id)
                    .orElseThrow(() -> new DishNotFoundException(id));
            Integer amount = pair.getValue();
            cookingTimes.addAll(Collections.nCopies(amount, dish.getCookingTime()));
        }
        return cookingTimes;
    }
}
