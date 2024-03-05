package com.example.app.main.mapper;

import com.example.app.main.data.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDataModelToOrderDtoMapper {
    private final DishDataModelToDishDtoMapper dishDataModelToDishDtoMapper;

    public com.example.api.dto.Order map(Order orderDbModel) {
        return com.example.api.dto.Order.builder()
                .id(orderDbModel.getId())
                .userId(orderDbModel.getUserId())
                .readyDishesAmount(orderDbModel.getReadyDishesAmount())
                .date(orderDbModel.getDate())
                .status(orderDbModel.getStatus())
                .isPaid(orderDbModel.getIsPaid())
                .dishes(orderDbModel.getDishes()
                        .stream()
                        .map(dishDataModelToDishDtoMapper::map)
                        .toList())
                .build();
    }
}
