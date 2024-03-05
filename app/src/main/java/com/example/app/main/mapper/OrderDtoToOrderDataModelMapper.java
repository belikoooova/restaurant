package com.example.app.main.mapper;

import com.example.app.main.data.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDtoToOrderDataModelMapper {
    private final DishDtoToDishDataModelMapper dishDtoToDishDataModelMapper;

    public Order map(com.example.api.dto.Order orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .status(orderDto.getStatus())
                .readyDishesAmount(orderDto.getReadyDishesAmount())
                .isPaid(orderDto.getIsPaid())
                .userId(orderDto.getUserId())
                .date(orderDto.getDate())
                .dishes(orderDto.getDishes()
                        .stream()
                        .map(dishDtoToDishDataModelMapper::map)
                        .toList())
                .build();
    }
}
